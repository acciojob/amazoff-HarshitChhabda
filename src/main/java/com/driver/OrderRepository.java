package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderMap;
    HashMap<String, DeliveryPartner> deliveryPartnerMap;

    HashMap<String, List<String>> pairMap;
    HashSet<String> isassignMap;

    OrderRepository() {
        this.orderMap = new HashMap<>();
        this.deliveryPartnerMap = new HashMap<>();
        this.pairMap = new HashMap<>();
        this.isassignMap = new HashSet<>();
    }

    public void addOrder(Order order) {
        if (!orderMap.containsKey(order.getId()))
            orderMap.put(order.getId(), order);
        isassignMap.add(order.getId());
    }

    public void addPartner(String partnerId) {
        if (!deliveryPartnerMap.containsKey(partnerId)) {
            DeliveryPartner d = new DeliveryPartner(partnerId);
            deliveryPartnerMap.put(partnerId, d);
        }
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if (deliveryPartnerMap.containsKey(partnerId) && orderMap.containsKey(orderId)) {
            if (!pairMap.containsKey(partnerId)) {
                List<String> assignOrderList = new ArrayList<>();
                assignOrderList.add(orderId);
                pairMap.put(partnerId, assignOrderList);
                deliveryPartnerMap.get(partnerId).setNumberOfOrders(deliveryPartnerMap.get(partnerId).getNumberOfOrders() + 1);
                isassignMap.remove(orderId);

            } else {
                List<String> assign = pairMap.get(partnerId);
                assign.add(orderId);
                pairMap.put(partnerId, assign);
                deliveryPartnerMap.get(partnerId).setNumberOfOrders(deliveryPartnerMap.get(partnerId).getNumberOfOrders() + 1);
                isassignMap.remove(orderId);
            }
        }
    }

    public Order getOrderById(String orderId) {
        if (!orderMap.containsKey(orderId)) {
            return null;
        }
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if (!deliveryPartnerMap.containsKey(partnerId)) return null;

        return deliveryPartnerMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        if (!pairMap.containsKey(partnerId)) return null;

        List<String> a = pairMap.get(partnerId);

        return a.size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {

        if (!pairMap.containsKey(partnerId)) return null;

        List<String> a = pairMap.get(partnerId);

        return a;
    }

    public List<String> getAllOrders() {
        List<String> ab = new ArrayList<>();
        for (String a : orderMap.keySet()) {
            ab.add(a);
        }
        return ab;
    }

    public Integer getCountOfUnassignedOrders() {
        return isassignMap.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count = 0;
        String arr[] = time.split(":");
        int h = Integer.parseInt(arr[0]);
        int m = Integer.parseInt(arr[1]);
        int total = h * 60 + m;

        List<String> s = pairMap.getOrDefault(partnerId, new ArrayList<>());

        if (s.size() == 0) return 0;

        for (String ss : s) {
            Order currentOrd = orderMap.get(ss);
            if (currentOrd.getDeliveryTime() > total) {
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        String str = "00:00";
        int max = 0;
        List<String> sa = pairMap.getOrDefault(partnerId, new ArrayList<>());
        if (sa.size() == 0) return str;

        for (String a : sa) {
            Order current = orderMap.get(a);
            max = Math.max(max, current.getDeliveryTime());
        }

        int h = max / 60;
        int m = max % 60;
        if (h < 10) {
            str = "0" + h + ":";
        } else {
            str = h + ":";
        }
        if (m < 10) {
            str += "0" + m;
        } else {
            str += m;
        }
        return str;
    }

    public void deletePartnerById(String partnerId) {
        if (!pairMap.isEmpty()) {
            isassignMap.addAll(pairMap.get(partnerId));
        }
        deliveryPartnerMap.remove(partnerId);

        pairMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        if (orderMap.containsKey(orderId)) {
            if (isassignMap.contains(orderId)) {
                isassignMap.remove(orderId);
            } else {
                for (String s : pairMap.keySet()) {
                    List<String> list = pairMap.get(s);
                    if (list.contains(orderId)) {
                        list.remove(orderId);
                    }
                }
            }

        }
    }
}
