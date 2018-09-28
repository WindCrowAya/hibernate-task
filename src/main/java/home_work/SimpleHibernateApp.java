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


            int[] ids = {1131, 1401, 2201, 1112};
            Product product = null;

            //поиск первого продукта, который имеется в наличии
            for (int id : ids) {
                Product loadedProduct = session.load(Product.class, id);
                int quantity = loadedProduct.getQuantityInWarehouse();
                String productName = loadedProduct.getProductName();

                if (quantity < 1) {
                    System.out.println("Товара \'" + productName + "\' нет в наличии");
                    continue;
                }
                System.out.println("Товар \'" + productName + "\' есть в наличии в количестве: " + quantity);
                product = loadedProduct;
                break;
            }

            if (product == null) {
                System.out.println("Таких товаров в наличии нет");
                return;
            }

            Order order = new Order();
            order.setAddress("Москва, Госпитальный пер. д. 4/6");
            order.setPaymentMethod("Visa");
            order.setLastUpdatedDate(new Date());
            order.setOrderStatus(false);

            System.out.println("Order Id before saving: " + order.getOrderId());
            session.save(order);
            System.out.println("Order Id after saving: " + order.getOrderId());
            System.out.println("Order saved");


            OrderProductId id = new OrderProductId(order, product);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantityOfProducts(1);
            orderProduct.setOrderProductId(id);

            System.out.println("OrderProductId in orderProduct, Order Id: " + orderProduct.getOrderProductId().getOrder().getOrderId());
            System.out.println("OrderProductId in orderProduct, Product Id: " + orderProduct.getOrderProductId().getProduct().getProductId());

            System.out.println("OrderProductId, Order Id: " + id.getOrder().getOrderId());
            System.out.println("OrderProductId, Product Id: " + id.getProduct().getProductId());
            session.save(orderProduct);
            System.out.println("OrderProduct saved");

            product.setQuantityInWarehouse(product.getQuantityInWarehouse() - 1);

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
//        System.exit(300);
    }
}
