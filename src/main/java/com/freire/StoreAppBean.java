package com.freire;

import com.freire.entity.Inventory;
import com.freire.entity.Store;
import com.freire.services.MainService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.transaction.RollbackException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@SessionScoped
@Named
public class StoreAppBean implements Serializable {

    @Size(min = 3, max = 20)
    @NotEmpty
    private String storeName;

    @Size(min = 3, max = 20)
    @NotEmpty
    private String location;

    @EJB
    private MainService mainService;

    public List<Store> getStoreList() {
        return mainService.getStoreList();
    }

    public String addStore() {
        Store store = new Store(storeName, location);
        Optional<Store> storeExists = mainService.getStoreList().stream().filter(p ->
                p.getStoreName().equals(storeName)).findFirst();
        if (!storeExists.isPresent()) {
            mainService.addStore(store);
        }
        clearFields();
        return "storeList";
    }

    public String delStore(){
        //Store store = new Store(storeName, location);
        Optional<Store> storeExists = mainService.getStoreList().stream().filter(p ->
                p.getStoreName().equals(storeName)).findFirst();
        if (storeExists.isPresent()) {
            try{
                mainService.removeStore(storeExists.get());
            }catch(Exception e){

            }
        }
        clearFields();
        return "storeList";
    }

//    public String updateStore(){
//        Store store = new Store(storeName, location);
//        Optional<Store> storeExists = mainService.getStoreList().stream().filter(p ->
//                p.getStoreName().equals(storeName)).findFirst();
//        if (storeExists.isPresent()) {
//            store.setId(storeExists.get().getId());
//            mainService.addStore(store);
//        }
//        clearFields();
//        return "storeList";
//    }

    private void clearFields() {
        setStoreName("");
        setLocation("");
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
