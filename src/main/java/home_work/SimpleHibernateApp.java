package home_work;

import home_work.entities.Order;
import home_work.entities.OrderProduct;
import home_work.entities.Product;
import home_work.entities.ids.OrderProductId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;

public class SimpleHibernateApp {
    public static void main(String[] args) {
        Transaction tx = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            tx = session.beginTransaction();

            //артикулы, по которым будем искать товар
            int[] ids = {1131, 1401, 2201, 1112};
            Product product = null;

            //поиск первого товара, который имеется в наличии
            for (int i = 0; i < ids.length; i++) {
                Product loadedProduct = session.load(Product.class, ids[i]);
                int quantity = loadedProduct.getQuantityInWarehouse();
                String productName = loadedProduct.getProductName();

                //если товара в наличии нет
                if (quantity < 1) {
                    System.out.println("Товара \'" + productName + "\' нет в наличии");
                    continue;
                }

                //если есть, то работаем в дальнейшим с этим товаром
                System.out.println("Товар \'" + productName + "\' есть в наличии в количестве: " + quantity);
                product = loadedProduct;
                break;
            }

            //если продукт не найден, то завершаем поиск
            if (product == null) {
                System.out.println("Таких товаров в наличии нет");
                return;
            }

            Order order = new Order();
            order.setAddress("Москва, Госпитальный пер. д. 4/6");
            order.setPaymentMethod("Visa");
            order.setLastUpdatedDate(new Date());
            order.setOrderStatus(false);

            //сохраняем заказ в БД
            session.save(order);

            OrderProductId id = new OrderProductId(order, product);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantityOfProducts(2);  //указываем количество товара
            orderProduct.setOrderProductId(id);

            //сохраняем конкретный товар заказа в БД
            session.save(orderProduct);

            //изменяем количество товара на складе
            product.setQuantityInWarehouse(product.getQuantityInWarehouse() - orderProduct.getQuantityOfProducts());

            tx.commit();
            System.out.println("Transaction has been committed");

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                System.out.println("Transaction has been rollbacked");
            }
            e.printStackTrace();
        } finally {
            session.close();
            System.out.println("Session has been closed");
            sessionFactory.close();
            System.out.println("SessionFactory has been closed");
        }

        System.out.println("End of the process");
    }
}
