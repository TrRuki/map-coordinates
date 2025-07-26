package me.trruki;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapDecorationsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class MapCoordinatesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("mapcoords")
					.executes(context -> {
						MinecraftClient client = MinecraftClient.getInstance();
						if (client.player != null){
							ItemStack mainItem = client.player.getInventory().getSelectedStack();
							if (mainItem == null || ! mainItem.isOf(Items.FILLED_MAP)){
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §cYou are not holding a treasure map"));
							} else if (mainItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray().length == 0) {
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §cYou are holding a map, but it's not a treasure map"));
							} else {
								MapDecorationsComponent.Decoration decoration = (MapDecorationsComponent.Decoration) mainItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray()[0];
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §aX: "+decoration.x()+" §8| §aZ: "+decoration.z()));
							}
						}
						return 1;
					}));
		});
	}
}