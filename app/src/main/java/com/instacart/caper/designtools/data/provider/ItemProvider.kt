package com.instacart.caper.designtools.data.provider

import com.instacart.caper.designtools.data.model.GroceryItem
import kotlin.random.Random

object ItemProvider {

    private val catalogItems = listOf(
        GroceryItem(
            imagePath = "images/catalog/brownies.png",
            title = "Ghirardelli - Dark Chocolate Premium Brownie Mix",
            price = 4.99,
            weight = "20 oz",
            barcode = "0000000041121"
        ),
        GroceryItem(
            imagePath = "images/catalog/cake.png",
            title = "Birthday Cake",
            price = 12.99,
            weight = "32 oz",
            barcode = "0000000041122"
        ),
        GroceryItem(
            imagePath = "images/catalog/chips.png",
            title = "Tostitos - Original",
            price = 3.99,
            weight = "12 oz",
            barcode = "0000000041123"
        ),
        GroceryItem(
            imagePath = "images/catalog/dog_food.png",
            title = "Purina ONE Dry Dog Food - Lamb & Rice",
            price = 29.99,
            weight = "15 lb",
            barcode = "0000000041124"
        ),
        GroceryItem(
            imagePath = "images/catalog/eggo.png",
            title = "Eggo Waffles",
            price = 4.99,
            weight = "12.3 oz",
            barcode = "0000000041125"
        ),
        GroceryItem(
            imagePath = "images/catalog/granola.png",
            title = "Catalina Crunch Cereal - Cinnamon Toast",
            price = 5.49,
            weight = "24 oz",
            barcode = "0000000041126"
        ),
        GroceryItem(
            imagePath = "images/catalog/ice_cream.png",
            title = "Häagen-Dazs Ice Cream - Chocolate",
            price = 4.99,
            weight = "14 oz",
            barcode = "0000000041127"
        ),
        GroceryItem(
            imagePath = "images/catalog/milk.png",
            title = "Whole Milk",
            price = 4.29,
            weight = "1 gal",
            barcode = "0000000041128"
        ),
        GroceryItem(
            imagePath = "images/catalog/pasta.png",
            title = "Banza Gluten-Free Chickpea Penne Pasta",
            price = 6.49,
            weight = "8 oz",
            barcode = "0000000041129"
        ),
        GroceryItem(
            imagePath = "images/catalog/peanut_butter.png",
            title = "Jif - Extra Crunchy Peanut Butter",
            price = 5.99,
            weight = "16 oz",
            barcode = "0000000041130"
        ),
        GroceryItem(
            imagePath = "images/catalog/sushi.png",
            title = "Fresh Sushi Roll",
            price = 9.99,
            weight = "8 oz",
            barcode = "0000000041131"
        ),
        GroceryItem(
            imagePath = "images/catalog/toilet_paper.png",
            title = "Charmin - Ultra Soft Toilet Paper",
            price = 14.99,
            weight = "12 rolls",
            barcode = "0000000041132"
        ),
        GroceryItem(
            imagePath = "images/catalog/tomato_sauce.png",
            title = "Hunt's Tomato Sauce",
            price = 2.99,
            weight = "24 oz",
            barcode = "0000000041133"
        ),
        GroceryItem(
            imagePath = "images/catalog/water.png",
            title = "Poland Spring Water",
            price = 7.99,
            weight = "24 pack",
            barcode = "0000000041134"
        ),
        GroceryItem(
            imagePath = "images/catalog/paper_towels.png",
            title = "Bounty Paper Towels - Select-A-Size",
            price = 19.99,
            weight = "8 rolls",
            barcode = "0000000041135"
        )
    )

    private val produceItems = listOf(
        GroceryItem(
            imagePath = "images/produce/apples_1.png",
            title = "Red Delicious Apples",
            price = 2.99,
            weight = "1.5 lb",
            barcode = "0000000042101"
        ),
        GroceryItem(
            imagePath = "images/produce/apples_2.png",
            title = "Gala Apples",
            price = 3.49,
            weight = "2 lb",
            barcode = "0000000042102"
        ),
        GroceryItem(
            imagePath = "images/produce/apples_3.png",
            title = "Granny Smith Apples",
            price = 3.29,
            weight = "1.8 lb",
            barcode = "0000000042103"
        ),
        GroceryItem(
            imagePath = "images/produce/avocado.png",
            title = "Fresh Avocado",
            price = 1.99,
            weight = "0.4 lb",
            barcode = "0000000042104"
        ),
        GroceryItem(
            imagePath = "images/produce/banana.png",
            title = "Yellow Bananas",
            price = 0.99,
            weight = "1 lb",
            barcode = "0000000042105"
        ),
        GroceryItem(
            imagePath = "images/produce/carrots.png",
            title = "Organic Carrots",
            price = 2.49,
            weight = "2 lb",
            barcode = "0000000042106"
        ),
        GroceryItem(
            imagePath = "images/produce/cucumber.png",
            title = "English Cucumber",
            price = 1.49,
            weight = "0.8 lb",
            barcode = "0000000042107"
        ),
        GroceryItem(
            imagePath = "images/produce/durian.png",
            title = "Durian",
            price = 12.99,
            weight = "3 lb",
            barcode = "0000000042108"
        ),
        GroceryItem(
            imagePath = "images/produce/lemon.png",
            title = "Fresh Lemon",
            price = 0.79,
            weight = "0.3 lb",
            barcode = "0000000042109"
        ),
        GroceryItem(
            imagePath = "images/produce/onion_1.png",
            title = "Yellow Onion",
            price = 1.29,
            weight = "0.5 lb",
            barcode = "0000000042110"
        ),
        GroceryItem(
            imagePath = "images/produce/onion_2.png",
            title = "Red Onion",
            price = 1.49,
            weight = "0.6 lb",
            barcode = "0000000042111"
        ),
        GroceryItem(
            imagePath = "images/produce/onion_3.png",
            title = "Sweet Onion",
            price = 1.79,
            weight = "0.7 lb",
            barcode = "0000000042112"
        ),
        GroceryItem(
            imagePath = "images/produce/orange_1.png",
            title = "Valencia Orange",
            price = 1.99,
            weight = "0.6 lb",
            barcode = "0000000042113"
        ),
        GroceryItem(
            imagePath = "images/produce/orange_2.png",
            title = "Mandarin Orange",
            price = 2.49,
            weight = "0.5 lb",
            barcode = "0000000042114"
        ),
        GroceryItem(
            imagePath = "images/produce/pear.png",
            title = "Bartlett Pear",
            price = 2.29,
            weight = "0.7 lb",
            barcode = "0000000042115"
        ),
        GroceryItem(
            imagePath = "images/produce/potato.png",
            title = "Russet Potato",
            price = 0.89,
            weight = "0.8 lb",
            barcode = "0000000042116"
        ),
        GroceryItem(
            imagePath = "images/produce/squash.png",
            title = "Butternut Squash",
            price = 2.99,
            weight = "1.2 lb",
            barcode = "0000000042117"
        ),
        GroceryItem(
            imagePath = "images/produce/sweet_potato.png",
            title = "Sweet Potato",
            price = 1.99,
            weight = "1 lb",
            barcode = "0000000042118"
        ),
        GroceryItem(
            imagePath = "images/produce/watermelon.png",
            title = "Watermelon",
            price = 6.99,
            weight = "8 lb",
            barcode = "0000000042119"
        )
    )

    /**
     * Returns a random catalog item from the in-memory list
     */
    fun getRandomCatalogItem(): GroceryItem {
        return catalogItems[Random.nextInt(catalogItems.size)]
    }

    /**
     * Returns a random produce item from the in-memory list
     */
    fun getRandomProduceItem(): GroceryItem {
        return produceItems[Random.nextInt(produceItems.size)]
    }

    /**
     * Returns all catalog items
     */
    fun getAllCatalogItems(): List<GroceryItem> = catalogItems

    /**
     * Returns all produce items
     */
    fun getAllProduceItems(): List<GroceryItem> = produceItems

    /**
     * Returns a random item from either catalog or produce
     */
    fun getRandomItem(): GroceryItem {
        return if (Random.nextBoolean()) {
            getRandomCatalogItem()
        } else {
            getRandomProduceItem()
        }
    }
}
