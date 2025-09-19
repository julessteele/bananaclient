package net.julessteele.bananaclient

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Banana : ModInitializer {

    val name: String = "Banana Client"
    val modid: String = "bananaclient"
    val logger: Logger = LoggerFactory.getLogger("bananaclient")

    // Resources not initialized yet... In a mod load ready state
	override fun onInitialize() {
		logger.info("Hello from the {} Mod Initializer!", name)
	}
}
