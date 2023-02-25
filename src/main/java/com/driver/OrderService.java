package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository repo;

    public void addOrder(Order order) {
        repo.addOrder(order);
    }

    public void addPartner(String partnerId) {
        repo.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        repo.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        return repo.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return repo.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {

        return repo.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return repo.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return repo.getAllOrders();
    }

    public Integer getCountOfUnassignedOrderss() {
        return repo.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerssId(String time, String partnerId) {
        return repo.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTimeByPartnerIdea(String partnerId) {
        return repo.getLastDeliveryTimeByPartnerId(partnerId);
    }
    public void deletePartnerById(String partnerId) {
        repo.deletePartnerById(partnerId);
    }
    public void deleteOrderById(String orderId) {
        repo.deleteOrderById(orderId);
    }
}
