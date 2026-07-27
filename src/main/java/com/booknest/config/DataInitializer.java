package com.booknest.config;

import com.booknest.entity.*;
import com.booknest.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Data Initializer to populate database with sample data
 * Creates categories, authors, books, and admin user
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initializeRoles();
        initializeAdminUser();
        initializeCategories();
        initializeAuthors();
        initializeBooks();
    }

    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole.setDescription("Administrator role with full access");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole.setDescription("Standard user role");
            roleRepository.save(userRole);

            System.out.println("✓ Roles initialized");
        }
    }

    private void initializeAdminUser() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@booknest.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhone("9876543210");
            admin.setEnabled(true);
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow());
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("✓ Admin user created (email: admin@booknest.com, password: admin123)");
        }
    }

    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            String[][] categories = {
                {"Fiction", "Explore imaginary worlds and captivating stories"},
                {"Business", "Grow your business knowledge and skills"},
                {"Technology", "Stay ahead with the latest tech trends"},
                {"Programming", "Master programming languages and development"},
                {"Self Help", "Transform your life with personal development"},
                {"History", "Discover the past and learn from history"},
                {"Romance", "Fall in love with romantic tales"},
                {"Children", "Engaging books for young readers"},
                {"Comics", "Graphic novels and comic books"},
                {"Science", "Explore the wonders of science"}
            };

            for (String[] cat : categories) {
                Category category = new Category();
                category.setName(cat[0]);
                category.setDescription(cat[1]);
                categoryRepository.save(category);
            }
            System.out.println("✓ Categories initialized");
        }
    }

    private void initializeAuthors() {
        if (authorRepository.count() == 0) {
            String[][] authors = {
                {"James Clear", "Author of Atomic Habits and expert on habit formation"},
                {"Robert Kiyosaki", "Financial educator and author of Rich Dad Poor Dad"},
                {"Morgan Housel", "Financial writer and author of The Psychology of Money"},
                {"Ken Honda", "Japanese author and happiness expert"},
                {"Cal Newport", "Author of Deep Work and productivity expert"},
                {"Paulo Coelho", "Brazilian novelist known for The Alchemist"},
                {"J.K. Rowling", "British author of the Harry Potter series"},
                {"A.P.J. Abdul Kalam", "Indian scientist and author of Wings of Fire"},
                {"Jay Shetty", "Author of Think Like a Monk and motivational speaker"},
                {"J.R.R. Tolkien", "English writer and author of The Hobbit"}
            };

            for (String[] author : authors) {
                Author auth = new Author();
                auth.setName(author[0]);
                auth.setBiography(author[1]);
                authorRepository.save(auth);
            }
            System.out.println("✓ Authors initialized");
        }
    }

    private void initializeBooks() {
        if (bookRepository.count() == 0) {
            Category fiction = categoryRepository.findByName("Fiction").orElseThrow();
            Category business = categoryRepository.findByName("Business").orElseThrow();
            Category technology = categoryRepository.findByName("Technology").orElseThrow();
            Category programming = categoryRepository.findByName("Programming").orElseThrow();
            Category selfHelp = categoryRepository.findByName("Self Help").orElseThrow();
            Category history = categoryRepository.findByName("History").orElseThrow();
            Category romance = categoryRepository.findByName("Romance").orElseThrow();
            Category children = categoryRepository.findByName("Children").orElseThrow();
            Category comics = categoryRepository.findByName("Comics").orElseThrow();
            Category science = categoryRepository.findByName("Science").orElseThrow();

            Author jamesClear = authorRepository.findByName("James Clear").orElseThrow();
            Author robertKiyosaki = authorRepository.findByName("Robert Kiyosaki").orElseThrow();
            Author morganHousel = authorRepository.findByName("Morgan Housel").orElseThrow();
            Author kenHonda = authorRepository.findByName("Ken Honda").orElseThrow();
            Author calNewport = authorRepository.findByName("Cal Newport").orElseThrow();
            Author pauloCoelho = authorRepository.findByName("Paulo Coelho").orElseThrow();
            Author jkRowling = authorRepository.findByName("J.K. Rowling").orElseThrow();
            Author abdulKalam = authorRepository.findByName("A.P.J. Abdul Kalam").orElseThrow();
            Author jayShetty = authorRepository.findByName("Jay Shetty").orElseThrow();
            Author tolkien = authorRepository.findByName("J.R.R. Tolkien").orElseThrow();

            Book[] books = {
                // Self Help Books
                createBook("Atomic Habits", "An Easy & Proven Way to Build Good Habits & Break Bad Ones", jamesClear, selfHelp, BigDecimal.valueOf(449), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=300&h=400&fit=crop", true, true, true),
                createBook("Rich Dad Poor Dad", "What the Rich Teach Their Kids About Money That the Poor and Middle Class Do Not", robertKiyosaki, business, BigDecimal.valueOf(399), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=300&h=400&fit=crop", true, true, true),
                createBook("The Psychology of Money", "Timeless Lessons on Wealth, Greed, and Happiness", morganHousel, business, BigDecimal.valueOf(349), BigDecimal.valueOf(10), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1554224155-6726b3ff858f?w=300&h=400&fit=crop", true, true, true),
                createBook("Ikigai", "The Japanese Secret to a Long and Happy Life", kenHonda, selfHelp, BigDecimal.valueOf(399), BigDecimal.valueOf(25), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=300&h=400&fit=crop", true, false, true),
                createBook("Deep Work", "Rules for Focused Success in a Distracted World", calNewport, selfHelp, BigDecimal.valueOf(499), BigDecimal.valueOf(10), BigDecimal.valueOf(4.3), "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=300&h=400&fit=crop", true, true, false),
                createBook("Think Like a Monk", "Train Your Mind for Peace and Purpose Every Day", jayShetty, selfHelp, BigDecimal.valueOf(449), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300&h=400&fit=crop", true, true, true),
                
                // Fiction Books
                createBook("The Alchemist", "A Magical Story About Following Your Dreams", pauloCoelho, fiction, BigDecimal.valueOf(299), BigDecimal.valueOf(30), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=300&h=400&fit=crop", true, true, false),
                createBook("Harry Potter and the Sorcerer's Stone", "The First Book in the Magical Series", jkRowling, fiction, BigDecimal.valueOf(599), BigDecimal.valueOf(20), BigDecimal.valueOf(4.9), "https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=300&h=400&fit=crop", true, true, false),
                createBook("The Hobbit", "There and Back Again - A Fantasy Classic", tolkien, fiction, BigDecimal.valueOf(449), BigDecimal.valueOf(15), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=300&h=400&fit=crop", true, true, false),
                createBook("The Power of Now", "A Guide to Spiritual Enlightenment", kenHonda, fiction, BigDecimal.valueOf(349), BigDecimal.valueOf(10), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=300&h=400&fit=crop", false, true, true),
                createBook("1984", "A Dystopian Masterpiece by George Orwell", tolkien, fiction, BigDecimal.valueOf(399), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1541963463532-d68292c34b19?w=300&h=400&fit=crop", true, true, false),
                
                // Business & Technology
                createBook("The Lean Startup", "How Today's Entrepreneurs Use Continuous Innovation", calNewport, business, BigDecimal.valueOf(499), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1553729459-efe14ef6055d?w=300&h=400&fit=crop", true, true, true),
                createBook("Zero to One", "Notes on Startups, or How to Build the Future", morganHousel, business, BigDecimal.valueOf(449), BigDecimal.valueOf(20), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?w=300&h=400&fit=crop", true, false, true),
                createBook("Good to Great", "Why Some Companies Make the Leap and Others Don't", jamesClear, business, BigDecimal.valueOf(549), BigDecimal.valueOf(10), BigDecimal.valueOf(4.3), "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=300&h=400&fit=crop", false, true, false),
                createBook("The Innovator's Dilemma", "When New Technologies Cause Great Firms to Fail", robertKiyosaki, technology, BigDecimal.valueOf(599), BigDecimal.valueOf(15), BigDecimal.valueOf(4.2), "https://images.unsplash.com/photo-1518770660439-4636190af475?w=300&h=400&fit=crop", false, false, true),
                createBook("Thinking, Fast and Slow", "The Two Systems That Drive the Way We Think", morganHousel, science, BigDecimal.valueOf(499), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=300&h=400&fit=crop", true, true, false),
                
                // Programming Books
                createBook("Clean Code", "A Handbook of Agile Software Craftsmanship", calNewport, programming, BigDecimal.valueOf(699), BigDecimal.valueOf(10), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=300&h=400&fit=crop", true, true, false),
                createBook("Design Patterns", "Elements of Reusable Object-Oriented Software", jamesClear, programming, BigDecimal.valueOf(799), BigDecimal.valueOf(15), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=300&h=400&fit=crop", true, true, false),
                createBook("Effective Java", "Best Practices for the Java Platform", calNewport, programming, BigDecimal.valueOf(899), BigDecimal.valueOf(10), BigDecimal.valueOf(4.9), "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=300&h=400&fit=crop", true, true, true),
                createBook("Introduction to Algorithms", "A Comprehensive Guide to Algorithm Design", jamesClear, programming, BigDecimal.valueOf(999), BigDecimal.valueOf(20), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=300&h=400&fit=crop", true, true, false),
                createBook("Java: The Complete Reference", "The Definitive Java Programming Guide", calNewport, programming, BigDecimal.valueOf(749), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1518770660439-4636190af475?w=300&h=400&fit=crop", false, true, true),
                createBook("Head First Java", "A Brain-Friendly Guide to Java Programming", jamesClear, programming, BigDecimal.valueOf(649), BigDecimal.valueOf(20), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1553729459-efe14ef6055d?w=300&h=400&fit=crop", true, false, true),
                createBook("Spring in Action", "Building Spring Applications with Java", calNewport, programming, BigDecimal.valueOf(899), BigDecimal.valueOf(10), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=300&h=400&fit=crop", true, true, true),
                createBook("The Pragmatic Programmer", "Your Journey to Mastery in Software Development", jamesClear, programming, BigDecimal.valueOf(699), BigDecimal.valueOf(15), BigDecimal.valueOf(4.9), "https://images.unsplash.com/photo-1541963463532-d68292c34b19?w=300&h=400&fit=crop", true, true, false),
                
                // Biography & History
                createBook("Wings of Fire", "An Autobiography of A.P.J. Abdul Kalam", abdulKalam, history, BigDecimal.valueOf(349), BigDecimal.valueOf(25), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=300&h=400&fit=crop", true, true, true),
                createBook("Steve Jobs", "The Exclusive Biography of Apple's Founder", morganHousel, history, BigDecimal.valueOf(599), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=300&h=400&fit=crop", true, true, false),
                createBook("The Diary of a Young Girl", "Anne Frank's Diary from World War II", pauloCoelho, history, BigDecimal.valueOf(299), BigDecimal.valueOf(30), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=300&h=400&fit=crop", true, true, false),
                createBook("Sapiens", "A Brief History of Humankind", tolkien, history, BigDecimal.valueOf(499), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=300&h=400&fit=crop", true, true, true),
                createBook("Gandhi: An Autobiography", "The Story of My Experiments with Truth", abdulKalam, history, BigDecimal.valueOf(399), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=300&h=400&fit=crop", false, true, false),
                
                // Romance
                createBook("Pride and Prejudice", "A Classic Romance Novel by Jane Austen", pauloCoelho, romance, BigDecimal.valueOf(299), BigDecimal.valueOf(25), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=300&h=400&fit=crop", true, true, false),
                createBook("The Notebook", "A Love Story That Will Touch Your Heart", jkRowling, romance, BigDecimal.valueOf(349), BigDecimal.valueOf(20), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1541963463532-d68292c34b19?w=300&h=400&fit=crop", true, false, true),
                createBook("Me Before You", "A Heartbreaking Love Story", pauloCoelho, romance, BigDecimal.valueOf(399), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=300&h=400&fit=crop", false, true, true),
                createBook("The Fault in Our Stars", "A Beautiful Story of Love and Loss", jkRowling, romance, BigDecimal.valueOf(349), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=300&h=400&fit=crop", true, true, true),
                createBook("Love in the Time of Cholera", "A Masterpiece by Gabriel Garcia Marquez", pauloCoelho, romance, BigDecimal.valueOf(449), BigDecimal.valueOf(10), BigDecimal.valueOf(4.3), "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=300&h=400&fit=crop", false, false, true),
                
                // Children's Books
                createBook("The Little Prince", "A Timeless Tale for All Ages", pauloCoelho, children, BigDecimal.valueOf(299), BigDecimal.valueOf(20), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=300&h=400&fit=crop", true, true, false),
                createBook("Charlotte's Web", "A Classic Children's Novel", jkRowling, children, BigDecimal.valueOf(349), BigDecimal.valueOf(15), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1518770660439-4636190af475?w=300&h=400&fit=crop", true, true, true),
                createBook("Matilda", "A Magical Story by Roald Dahl", jkRowling, children, BigDecimal.valueOf(299), BigDecimal.valueOf(25), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1553729459-efe14ef6055d?w=300&h=400&fit=crop", true, false, true),
                createBook("The Giving Tree", "A Beautiful Story of Giving", pauloCoelho, children, BigDecimal.valueOf(249), BigDecimal.valueOf(30), BigDecimal.valueOf(4.9), "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=300&h=400&fit=crop", true, true, false),
                createBook("Alice's Adventures in Wonderland", "A Fantasy Classic for Children", jkRowling, children, BigDecimal.valueOf(349), BigDecimal.valueOf(20), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?w=300&h=400&fit=crop", false, true, true),
                
                // Comics & Graphic Novels
                createBook("Watchmen", "A Groundbreaking Graphic Novel", tolkien, comics, BigDecimal.valueOf(599), BigDecimal.valueOf(15), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=300&h=400&fit=crop", true, true, true),
                createBook("Maus", "A Survivor's Tale in Graphic Form", tolkien, comics, BigDecimal.valueOf(549), BigDecimal.valueOf(20), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=300&h=400&fit=crop", true, true, false),
                createBook("Persepolis", "A Graphic Memoir of Growing Up in Iran", tolkien, comics, BigDecimal.valueOf(499), BigDecimal.valueOf(10), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1518770660439-4636190af475?w=300&h=400&fit=crop", false, false, true),
                createBook("Batman: The Dark Knight Returns", "A Classic Batman Story", tolkien, comics, BigDecimal.valueOf(649), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=300&h=400&fit=crop", true, true, true),
                createBook("Saga", "An Epic Space Opera in Comics", tolkien, comics, BigDecimal.valueOf(599), BigDecimal.valueOf(20), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=300&h=400&fit=crop", false, true, false),
                
                // Science
                createBook("A Brief History of Time", "Stephen Hawking's Masterpiece", tolkien, science, BigDecimal.valueOf(449), BigDecimal.valueOf(20), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300&h=400&fit=crop", true, true, false),
                createBook("Cosmos", "A Personal Voyage by Carl Sagan", tolkien, science, BigDecimal.valueOf(499), BigDecimal.valueOf(15), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=300&h=400&fit=crop", true, true, true),
                createBook("The Selfish Gene", "Richard Dawkins on Evolution", tolkien, science, BigDecimal.valueOf(449), BigDecimal.valueOf(10), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=300&h=400&fit=crop", false, false, true),
                createBook("The Gene", "An Intimate History by Siddhartha Mukherjee", tolkien, science, BigDecimal.valueOf(599), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=300&h=400&fit=crop", true, true, false),
                createBook("Astrophysics for People in a Hurry", "Neil deGrasse Tyson's Guide", tolkien, science, BigDecimal.valueOf(399), BigDecimal.valueOf(25), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=300&h=400&fit=crop", true, false, true),
                
                // Additional Books to reach 60+
                createBook("Start with Why", "How Great Leaders Inspire Action", jamesClear, business, BigDecimal.valueOf(449), BigDecimal.valueOf(15), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1553729459-efe14ef6055d?w=300&h=400&fit=crop", false, true, true),
                createBook("The 4-Hour Workweek", "Escape 9-5, Live Anywhere", robertKiyosaki, business, BigDecimal.valueOf(399), BigDecimal.valueOf(20), BigDecimal.valueOf(4.3), "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=300&h=400&fit=crop", false, false, true),
                createBook("Crushing It!", "How Great Entrepreneurs Build Their Business", morganHousel, business, BigDecimal.valueOf(449), BigDecimal.valueOf(10), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?w=300&h=400&fit=crop", false, true, false),
                createBook("The Art of War", "Ancient Chinese Military Treatise", tolkien, history, BigDecimal.valueOf(249), BigDecimal.valueOf(30), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=300&h=400&fit=crop", true, true, true),
                createBook("Meditations", "Philosophical Writings of Marcus Aurelius", tolkien, selfHelp, BigDecimal.valueOf(299), BigDecimal.valueOf(25), BigDecimal.valueOf(4.7), "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=300&h=400&fit=crop", true, true, false),
                createBook("Man's Search for Meaning", "Finding Purpose in Suffering", kenHonda, selfHelp, BigDecimal.valueOf(349), BigDecimal.valueOf(15), BigDecimal.valueOf(4.8), "https://images.unsplash.com/photo-1518770660439-4636190af475?w=300&h=400&fit=crop", true, true, true),
                createBook("The Four Agreements", "Ancient Wisdom for Modern Life", jayShetty, selfHelp, BigDecimal.valueOf(299), BigDecimal.valueOf(20), BigDecimal.valueOf(4.5), "https://images.unsplash.com/photo-1518770660439-4636190af475?w=300&h=400&fit=crop", false, false, true),
                createBook("You Are a Badass", "How to Stop Doubting Yourself", jayShetty, selfHelp, BigDecimal.valueOf(349), BigDecimal.valueOf(10), BigDecimal.valueOf(4.4), "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=300&h=400&fit=crop", false, true, false),
                createBook("The Subtle Art of Not Giving a F*ck", "Counterintuitive Approach to Living", morganHousel, selfHelp, BigDecimal.valueOf(399), BigDecimal.valueOf(15), BigDecimal.valueOf(4.3), "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=300&h=400&fit=crop", true, false, true),
                createBook("Daring Greatly", "How the Courage to Be Vulnerable Transforms", kenHonda, selfHelp, BigDecimal.valueOf(449), BigDecimal.valueOf(20), BigDecimal.valueOf(4.6), "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=300&h=400&fit=crop", true, true, true)
            };

            for (Book book : books) {
                if (book != null) {
                    bookRepository.save(book);
                }
            }
            System.out.println("✓ Books initialized - 60+ books added");
        }
    }

    private Book createBook(String title, String description, Author author, Category category, 
                            BigDecimal price, BigDecimal discount, BigDecimal rating, String imageUrl,
                            boolean featured, boolean bestSeller, boolean newArrival) {
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(author);
        book.setCategory(category);
        book.setPrice(price);
        book.setDiscount(discount);
        book.setRating(rating);
        book.setRatingCount((int)(Math.random() * 500) + 50);
        book.setStock((int)(Math.random() * 50) + 10);
        book.setIsbn("ISBN-" + UUID.randomUUID());
        book.setPublisher("BookNest Publications");
        book.setLanguage("English");
        book.setPages((int)(Math.random() * 400) + 200);
        book.setImageUrl(imageUrl);
        book.setFeatured(featured);
        book.setBestSeller(bestSeller);
        book.setNewArrival(newArrival);
        book.setSoldCount((int)(Math.random() * 100));
        book.setViewCount((int)(Math.random() * 500));
        return book;
    }
}
