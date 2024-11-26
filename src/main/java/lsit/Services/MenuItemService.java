package lsit.Services;

import lsit.Exceptions.InvalidInputException;
import lsit.Exceptions.ResourceNotFoundException;
import lsit.Models.MenuItem;
import lsit.Repositories.MenuItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

/**
 * Service class for managing menu items.
 */
@Service
public class MenuItemService {
    private static final Logger logger = LoggerFactory.getLogger(MenuItemService.class);

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * Retrieves all menu items.
     *
     * @return List of all menu items.
     */
    public List<MenuItem> getAllMenuItems() {
        logger.info("Fetching all menu items");
        return menuItemRepository.list();
    }

    /**
     * Retrieves a menu item by its ID.
     *
     * @param id The UUID of the menu item.
     * @return The MenuItem object if found.
     * @throws ResourceNotFoundException if the menu item is not found.
     */
    public MenuItem getMenuItemById(UUID id) {
        logger.info("Fetching menu item with ID: {}", id);
        MenuItem menuItem = menuItemRepository.get(id);
        if (menuItem == null) {
            logger.error("Menu item not found with ID: {}", id);
            throw new ResourceNotFoundException("Invalid Menu item ID");
        }
        return menuItem;
    }

    /**
     * Adds a new menu item.
     *
     * @param menuItem The MenuItem object to add.
     * @return The added MenuItem object.
     */
    public MenuItem addMenuItem(MenuItem menuItem) {
        logger.info("Adding new menu item: {}", menuItem.getId());
        menuItemRepository.add(menuItem);
        return menuItem;
    }

    /**
     * Updates an existing menu item.
     *
     * @param menuItemDetails The MenuItem object containing updated details.
     * @return The updated MenuItem object.
     * @throws ResourceNotFoundException if the menu item is not found.
     */
    public MenuItem updateMenuItem(MenuItem menuItemDetails) {
        UUID id = menuItemDetails.getId();
        logger.info("Updating menu item with ID: {}", id);
        MenuItem existingMenuItem = menuItemRepository.get(id);
        if (existingMenuItem == null) {
            logger.error("Menu item not found with ID: {}", id);
            throw new InvalidInputException("Invalid Menu item ID");
        }
        
        if (menuItemDetails.getName() != null) {
            existingMenuItem.setName(menuItemDetails.getName());
        }
        if (menuItemDetails.getDescription() != null) {
            existingMenuItem.setDescription(menuItemDetails.getDescription());
        }
        if (menuItemDetails.getPrice() != 0) {
            existingMenuItem.setPrice(menuItemDetails.getPrice());
        }
        if (menuItemDetails.getSize() != null) {
            existingMenuItem.setSize(menuItemDetails.getSize());
        }
        if (menuItemDetails.getListOfAllergies() != null) {
            existingMenuItem.setListOfAllergies(menuItemDetails.getListOfAllergies());
        }
    
        menuItemRepository.update(existingMenuItem);
        return existingMenuItem;
    }

    /**
     * Deletes a menu item by its ID.
     *
     * @param id The UUID of the menu item to delete.
     * @throws ResourceNotFoundException if the menu item is not found.
     */
    public void deleteMenuItem(UUID id) {
        logger.info("Deleting menu item with ID: {}", id);
        MenuItem menuItem = menuItemRepository.get(id);
        if (menuItem == null) {
            logger.error("Menu item not found with ID: {}", id);
            throw new ResourceNotFoundException("Invalid Menu item ID");
        }
        menuItemRepository.remove(id);
    }

}
