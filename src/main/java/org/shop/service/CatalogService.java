package org.shop.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.shop.domain.Item;
import org.shop.domain.Product;
import org.shop.domain.Category;
import org.shop.util.HibernateUtil;

import java.util.List;

public class CatalogService {
    public List<Category> getCategoryList() {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        List<Category> list = session.createQuery("from Category ").list();
        ts.commit();
        HibernateUtil.closeSession();
        return list;
    }

    public Category getCategory(String categoryId) {

        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        Category category = (Category)session.get(Category.class,categoryId);
        ts.commit();
        System.out.println(category.getCategoryId());
        HibernateUtil.closeSession();;
        return category;
    }

    public Product getProduct(String productId) {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        Product product = (Product) session.get(Product.class,productId);
        ts.commit();
        System.out.println("catalogservice"+product.getProductId());
        HibernateUtil.closeSession();
        return product;
    }

    public List<Product> getProductListByCategory(String categoryId) {
        Session session=HibernateUtil.getSession();
        Transaction tx=session.beginTransaction();
        Query query=session.createQuery("from Product product where product.categoryId=?1");
        query.setParameter(1, categoryId);
        List<Product> list=query.list();
        tx.commit();
        System.out.println(list.get(0).getProductId());
        HibernateUtil.closeSession();
        return list;
    }

    // TODO enable using more than one keyword
    public List<Product> searchProductList(String keyword) {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        String hql = "from Product as s where s.name like '%"+keyword+"%' and s.categoryId like '%"+keyword+"%'";
        Query query = session.createQuery(hql);
        List list = query.list();
        HibernateUtil.closeSession();
        return list;
    }

    public List<Item> getItemListByProduct(String productId) {
        Session session=HibernateUtil.getSession();
        Transaction tx=session.beginTransaction();
        Query query=session.createQuery("from Item item where item.productId=?1");
        query.setParameter(1, productId);
        List<Item> list=query.list();
        tx.commit();
        HibernateUtil.closeSession();
        return list;
    }

    public Item getItem(String itemId) {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        Item item = (Item) session.get(Item.class,itemId);
        ts.commit();
        HibernateUtil.closeSession();
        return item;
    }

    public boolean isItemInStock(String itemId) {
        Session session = HibernateUtil.getSession();
        Transaction ts = session.beginTransaction();
        Item item = (Item) session.get(Item.class,itemId);
        ts.commit();
        HibernateUtil.closeSession();
        if (item != null)
            return true;
        return false;
    }
}
