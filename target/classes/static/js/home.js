document.addEventListener('DOMContentLoaded', function () {
    const reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;

    if (window.AOS) {
        AOS.init({
            duration: 850,
            once: true,
            offset: 120,
            easing: 'ease-out-cubic'
        });
    }

    const loader = document.querySelector('.page-loader');
    const heroTitle = document.querySelector('.hero-title');
    const heroSubtitle = document.querySelector('.hero-subtitle');
    const heroButtons = document.querySelectorAll('.hero-actions .btn');
    const heroStats = document.querySelectorAll('.hero-stats > div');
    const heroArt = document.querySelector('.hero-illustration');
    const floatingBooks = document.querySelectorAll('.floating-book');
    const navbar = document.querySelector('.navbar');
    const backToTop = document.getElementById('backToTop');

    document.body.classList.add('is-loading');

    const hideLoader = function () {
        if (!loader) {
            document.body.classList.remove('is-loading');
            return;
        }

        if (loader.classList.contains('is-hidden')) {
            document.body.classList.remove('is-loading');
            return;
        }

        if (reducedMotion || !window.gsap) {
            loader.classList.add('is-hidden');
            document.body.classList.remove('is-loading');
            return;
        }

        gsap.to(loader, {
            autoAlpha: 0,
            duration: 0.6,
            ease: 'power2.out',
            onComplete: function () {
                loader.classList.add('is-hidden');
                document.body.classList.remove('is-loading');
            }
        });
    };

    if (loader) {
        window.addEventListener('load', hideLoader, { once: true });
        window.setTimeout(hideLoader, 1000);
    }

    if (window.gsap && !reducedMotion) {
        const tl = gsap.timeline({ defaults: { ease: 'power3.out' } });
        tl.fromTo('.hero-copy', { autoAlpha: 0, y: 24 }, { autoAlpha: 1, y: 0, duration: 0.9 })
          .to(heroTitle, { autoAlpha: 1, y: 0, duration: 0.75 }, '-=0.35')
          .to(heroSubtitle, { autoAlpha: 1, y: 0, duration: 0.6 }, '-=0.2')
          .to(heroButtons, { autoAlpha: 1, y: 0, stagger: 0.12, duration: 0.55 }, '-=0.15')
          .to(heroStats, { autoAlpha: 1, y: 0, stagger: 0.1, duration: 0.45 }, '-=0.1')
          .to(heroArt, { autoAlpha: 1, scale: 1, duration: 0.8 }, '-=0.2')
          .to(floatingBooks, { autoAlpha: 1, y: 0, stagger: 0.08, duration: 0.5 }, '-=0.35');

        gsap.to('.floating-book', {
            y: '-=8',
            duration: 2.8,
            repeat: -1,
            yoyo: true,
            ease: 'sine.inOut'
        });
    } else {
        if (heroTitle) heroTitle.style.opacity = '1';
        if (heroSubtitle) heroSubtitle.style.opacity = '1';
        document.querySelectorAll('.hero-actions .btn').forEach(function (btn) { btn.style.opacity = '1'; });
        document.querySelectorAll('.hero-stats > div').forEach(function (stat) { stat.style.opacity = '1'; });
        if (heroArt) heroArt.style.opacity = '1';
        document.querySelectorAll('.floating-book').forEach(function (book) { book.style.opacity = '1'; });
    }

    if (navbar) {
        const updateNavbar = function () {
            navbar.classList.toggle('scrolled', window.scrollY > 20);
        };
        updateNavbar();
        window.addEventListener('scroll', updateNavbar, { passive: true });
    }

    document.querySelectorAll('.btn').forEach(function (button) {
        button.addEventListener('click', function (event) {
            if (reducedMotion) return;
            const ripple = document.createElement('span');
            ripple.className = 'ripple';
            ripple.style.left = event.clientX - button.getBoundingClientRect().left + 'px';
            ripple.style.top = event.clientY - button.getBoundingClientRect().top + 'px';
            button.appendChild(ripple);
            setTimeout(function () {
                ripple.remove();
            }, 400);
        });
    });

    if (heroArt && !reducedMotion) {
        heroArt.addEventListener('mousemove', function (event) {
            const rect = heroArt.getBoundingClientRect();
            const x = ((event.clientX - rect.left) / rect.width - 0.5) * 10;
            const y = ((event.clientY - rect.top) / rect.height - 0.5) * 10;
            gsap.to(heroArt, { rotateY: x, rotateX: -y, duration: 0.45, ease: 'power2.out' });
        });

        heroArt.addEventListener('mouseleave', function () {
            gsap.to(heroArt, { rotateY: 0, rotateX: 0, duration: 0.45, ease: 'power2.out' });
        });
    }

    const statObserver = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (!entry.isIntersecting) return;
            const stat = entry.target;
            const targetValue = Number(String(stat.getAttribute('data-target') || '0').replace(/[^0-9]/g, ''));
            const suffix = stat.getAttribute('data-suffix') || '';
            let current = 0;
            const duration = 1200;
            const startTime = performance.now();

            const tick = function (now) {
                const progress = Math.min((now - startTime) / duration, 1);
                const eased = 1 - Math.pow(1 - progress, 3);
                current = Math.round(targetValue * eased);
                stat.textContent = current.toLocaleString() + suffix;
                if (progress < 1) {
                    requestAnimationFrame(tick);
                }
            };

            requestAnimationFrame(tick);
            statObserver.unobserve(stat);
        });
    }, { threshold: 0.6 });

    document.querySelectorAll('.stat-number').forEach(function (stat) {
        statObserver.observe(stat);
    });

    if (backToTop) {
        const toggleBackToTop = function () {
            backToTop.classList.toggle('is-visible', window.scrollY > 320);
        };
        toggleBackToTop();
        window.addEventListener('scroll', toggleBackToTop, { passive: true });
        backToTop.addEventListener('click', function () {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }
});
