package org.hubert_lasota.BusinessManagement.entity.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAddress {
    private static final Map<Long, List<Long>> addressRelationToCustomers = new HashMap<>();

    private CustomerAddress() { }


    public static void addRelation(Long addressId, Long customerId) {
        if(addressRelationToCustomers.containsKey(addressId)) {
            addressRelationToCustomers.get(addressId).add(customerId);
        } else {
            List<Long> customers = new ArrayList<>();
            customers.add(customerId);
            addressRelationToCustomers.put(addressId, customers);
        }
    }

    public static boolean isAddressAssociatedToAnyCustomer(Long addressId) {
        return addressRelationToCustomers.containsKey(addressId);
    }

    public static boolean isAddressAssociatedToCustomer(Long addressId, Long customer) {
        if(!isAddressAssociatedToAnyCustomer(addressId)) {
            return false;
        }

        return addressRelationToCustomers.get(addressId).contains(customer);
    }

    public static void deleteRelation(Long addressId, Long customerId) {
        if(!addressRelationToCustomers.containsKey(addressId)) {
            return;
        }
        List<Long> customers = addressRelationToCustomers.get(addressId);
        if(customers.size() > 1) {
            customers.remove(customerId);
        } else {
            addressRelationToCustomers.remove(addressId, customers);
        }
    }

}
