package com.freire;

import com.freire.entity.Inventory;
import com.freire.entity.Store;
import com.freire.services.MainService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SessionScoped
@Named
public class StockAppBean implements Serializable {

    @Size(min = 3, max = 20)
    @NotEmpty
    private String storeName;

    @Size(min = 5, max = 20)
    @NotEmpty
    private String prodName;

    @EJB
    private MainService mainService;

    public List<Store> getStoreList() {
        return mainService.getStoreList().stream().filter(p ->
                p.getStoreName().equals(storeName)).collect(Collectors.toList());
    }

    public String addInvToStore() {
        Optional<Store> storeExists = mainService.getStoreList().stream().filter(p ->
                p.getStoreName().equals(storeName)).findFirst();
        Optional<Inventory> inventoryExists = mainService.getInventoryList().stream().filter(p ->
                p.getProdName().equals(prodName)).findFirst();
        if (storeExists.isPresent() && inventoryExists.isPresent()) {
            mainService.addInventoryToStore(storeExists.get(), inventoryExists.get());
        }
        clearFields();
        return "stockList";
    }

    public String delInvFromStore() {
        Optional<Store> storeExists = mainService.getStoreList().stream().filter(p ->
                p.getStoreName().equals(storeName)).findFirst();
        Optional<Inventory> inventoryExists = mainService.getInventoryList().stream().filter(p ->
                p.getProdName().equals(prodName)).findFirst();
        if (storeExists.isPresent() && inventoryExists.isPresent()) {
            mainService.delInventoryFromStore(storeExists.get(), inventoryExists.get());
        }
        clearFields();
        return "stockList";
    }

    private void clearFields() {
        setProdName("");
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}
