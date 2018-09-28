package home_work;

import home_work.entities.Category;
import home_work.entities.Order;
import home_work.entities.OrderProduct;
import home_work.entities.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(Category.class)
                         .addAnnotatedClass(Product.class)
                         .addAnnotatedClass(Order.class)
                         .addAnnotatedClass(OrderProduct.class);

            configuration.configure();
            return configuration
                    .buildSessionFactory(new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error building the factory");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
