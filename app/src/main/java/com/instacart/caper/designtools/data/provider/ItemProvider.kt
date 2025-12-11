package com.instacart.caper.designtools.data.provider

import com.instacart.caper.designtools.data.model.GroceryItem
import kotlin.random.Random

object ItemProvider {

    private val catalogItems = listOf(
        // 0
        GroceryItem(
            imagePath = "images/catalog/brownies.png",
            name = "Ghirardelli - Dark Chocolate Premium Brownie Mix",
            price = 4.99,
            weight = "20 oz",
            barcode = "0000000041121"
        ),
        // 1
        GroceryItem(
            imagePath = "images/catalog/cake.png",
            name = "Birthday Cake",
            price = 12.99,
            weight = "32 oz",
            barcode = "0000000041122"
        ),
        // 2
        GroceryItem(
            imagePath = "images/catalog/chips.png",
            name = "Tostitos - Original",
            price = 3.99,
            weight = "12 oz",
            barcode = "0000000041123"
        ),
        // 3
        GroceryItem(
            imagePath = "images/catalog/dog_food.png",
            name = "Purina ONE Dry Dog Food - Lamb & Rice",
            price = 29.99,
            weight = "15 lb",
            barcode = "0000000041124"
        ),
        // 4
        GroceryItem(
            imagePath = "images/catalog/eggo.png",
            name = "Eggo Waffles",
            price = 4.99,
            weight = "12.3 oz",
            barcode = "0000000041125"
        ),
        // 5
        GroceryItem(
            imagePath = "images/catalog/granola.png",
            name = "Catalina Crunch Cereal - Cinnamon Toast",
            price = 5.49,
            weight = "24 oz",
            barcode = "0000000041126"
        ),
        // 6
        GroceryItem(
            imagePath = "images/catalog/ice_cream.png",
            name = "Häagen-Dazs Ice Cream - Chocolate",
            price = 4.99,
            weight = "14 oz",
            barcode = "0000000041127"
        ),
        // 7
        GroceryItem(
            imagePath = "images/catalog/milk.png",
            name = "Whole Milk",
            price = 4.29,
            weight = "1 gal",
            barcode = "0000000041128"
        ),
        // 8
        GroceryItem(
            imagePath = "images/catalog/pasta.png",
            name = "Banza Gluten-Free Chickpea Penne Pasta",
            price = 6.49,
            weight = "8 oz",
            barcode = "0000000041129"
        ),
        // 9
        GroceryItem(
            imagePath = "images/catalog/peanut_butter.png",
            name = "Jif - Extra Crunchy Peanut Butter",
            price = 5.99,
            weight = "16 oz",
            barcode = "0000000041130"
        ),
        // 10
        GroceryItem(
            imagePath = "images/catalog/sushi.png",
            name = "Fresh Sushi Roll",
            price = 9.99,
            weight = "8 oz",
            barcode = "0000000041131"
        ),
        // 11
        GroceryItem(
            imagePath = "images/catalog/toilet_paper.png",
            name = "Charmin - Ultra Soft Toilet Paper",
            price = 14.99,
            weight = "12 rolls",
            barcode = "0000000041132"
        ),
        // 12
        GroceryItem(
            imagePath = "images/catalog/tomato_sauce.png",
            name = "Hunt's Tomato Sauce",
            price = 2.99,
            weight = "24 oz",
            barcode = "0000000041133"
        ),
        // 13
        GroceryItem(
            imagePath = "images/catalog/water.png",
            name = "Poland Spring Water",
            price = 7.99,
            weight = "24 pack",
            barcode = "0000000041134"
        ),
        // 14
        GroceryItem(
            imagePath = "images/catalog/paper_towels.png",
            name = "Bounty Paper Towels - Select-A-Size",
            price = 19.99,
            weight = "8 rolls",
            barcode = "0000000041135"
        )
    )

    private val produceItems = listOf(
        GroceryItem(
            imagePath = "images/produce/apples_1.png",
            name = "Red Delicious Apples",
            price = 2.99,
            weight = "1.5 lb",
            barcode = "0000000042101"
        ),
        GroceryItem(
            imagePath = "images/produce/apples_2.png",
            name = "Gala Apples",
            price = 3.49,
            weight = "2 lb",
            barcode = "0000000042102"
        ),
        GroceryItem(
            imagePath = "images/produce/apples_3.png",
            name = "Granny Smith Apples",
            price = 3.29,
            weight = "1.8 lb",
            barcode = "0000000042103"
        ),
        GroceryItem(
            imagePath = "images/produce/avocado.png",
            name = "Fresh Avocado",
            price = 1.99,
            weight = "0.4 lb",
            barcode = "0000000042104"
        ),
        GroceryItem(
            imagePath = "images/produce/banana.png",
            name = "Yellow Bananas",
            price = 0.99,
            weight = "1 lb",
            barcode = "0000000042105"
        ),
        GroceryItem(
            imagePath = "images/produce/carrots.png",
            name = "Organic Carrots",
            price = 2.49,
            weight = "2 lb",
            barcode = "0000000042106"
        ),
        GroceryItem(
            imagePath = "images/produce/cucumber.png",
            name = "English Cucumber",
            price = 1.49,
            weight = "0.8 lb",
            barcode = "0000000042107"
        ),
        GroceryItem(
            imagePath = "images/produce/durian.png",
            name = "Durian",
            price = 12.99,
            weight = "3 lb",
            barcode = "0000000042108"
        ),
        GroceryItem(
            imagePath = "images/produce/lemon.png",
            name = "Fresh Lemon",
            price = 0.79,
            weight = "0.3 lb",
            barcode = "0000000042109"
        ),
        GroceryItem(
            imagePath = "images/produce/onion_1.png",
            name = "Yellow Onion",
            price = 1.29,
            weight = "0.5 lb",
            barcode = "0000000042110"
        ),
        GroceryItem(
            imagePath = "images/produce/onion_2.png",
            name = "Red Onion",
            price = 1.49,
            weight = "0.6 lb",
            barcode = "0000000042111"
        ),
        GroceryItem(
            imagePath = "images/produce/onion_3.png",
            name = "Sweet Onion",
            price = 1.79,
            weight = "0.7 lb",
            barcode = "0000000042112"
        ),
        GroceryItem(
            imagePath = "images/produce/orange_1.png",
            name = "Valencia Orange",
            price = 1.99,
            weight = "0.6 lb",
            barcode = "0000000042113"
        ),
        GroceryItem(
            imagePath = "images/produce/orange_2.png",
            name = "Mandarin Orange",
            price = 2.49,
            weight = "0.5 lb",
            barcode = "0000000042114"
        ),
        GroceryItem(
            imagePath = "images/produce/pear.png",
            name = "Bartlett Pear",
            price = 2.29,
            weight = "0.7 lb",
            barcode = "0000000042115"
        ),
        GroceryItem(
            imagePath = "images/produce/potato.png",
            name = "Russet Potato",
            price = 0.89,
            weight = "0.8 lb",
            barcode = "0000000042116"
        ),
        GroceryItem(
            imagePath = "images/produce/squash.png",
            name = "Butternut Squash",
            price = 2.99,
            weight = "1.2 lb",
            barcode = "0000000042117"
        ),
        GroceryItem(
            imagePath = "images/produce/sweet_potato.png",
            name = "Sweet Potato",
            price = 1.99,
            weight = "1 lb",
            barcode = "0000000042118"
        ),
        GroceryItem(
            imagePath = "images/produce/watermelon.png",
            name = "Watermelon",
            price = 6.99,
            weight = "8 lb",
            barcode = "0000000042119"
        ),
        GroceryItem(
            imagePath = "images/produce/mac_cheese.png",
            name = "Mac and Cheese",
            price = 5.99,
            weight = "1.5 lb",
            barcode = "0000000042120"
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
