package com.freire;

import com.freire.entity.Inventory;
import com.freire.services.MainService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@SessionScoped
@Named
public class InventoryAppBean implements Serializable {

    @Size(min = 5, max = 20)
    @NotEmpty
    private String prodName;

    @Size(min = 3, max = 20)
    @NotEmpty
    private String sport;

    private int quantity;

    @EJB
    private MainService mainService;

    public List<Inventory> getInventoryList() {
        return mainService.getInventoryList();
    }

    public String addInventory() {
        Inventory inventory = new Inventory(prodName, sport, quantity);
        Optional<Inventory> inventoryExists = mainService.getInventoryList().stream().filter(p ->
                p.getProdName().equals(prodName)).findFirst();
        if (!inventoryExists.isPresent()) {
            mainService.addInventory(inventory);
        }
        clearFields();
        return "inventoryList";
    }

    public String delInventory(){
        //Inventory inventory = new Inventory(prodName, sport, quantity);
        Optional<Inventory> inventoryExists = mainService.getInventoryList().stream().filter(p ->
                p.getProdName().equals(prodName)).findFirst();
        if (inventoryExists.isPresent()) {
            mainService.removeInventory(inventoryExists.get());
        }
        clearFields();
        return "inventoryList";
    }

    public String updateInventory(){
        Inventory inventory = new Inventory(prodName, sport, quantity);
        Optional<Inventory> inventoryExists = mainService.getInventoryList().stream().filter(p ->
                p.getProdName().equals(prodName)).findFirst();
        if (inventoryExists.isPresent()) {
            inventory.setId(inventoryExists.get().getId());
            mainService.updInventory(inventory);
        }
        clearFields();
        return "inventoryList";
    }

    private void clearFields() {
        setProdName("");
        setSport("");
        setQuantity(0);
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
