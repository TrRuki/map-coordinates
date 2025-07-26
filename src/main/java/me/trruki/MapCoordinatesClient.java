package me.trruki;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapDecorationsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

public class MapCoordinatesClient implements ClientModInitializer {

	private double x;

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("mapcoords")
					.executes(context -> {
						MinecraftClient client = MinecraftClient.getInstance();
						if (client.player != null){
							ItemStack mainItem = client.player.getInventory().getSelectedStack();
							ItemStack offItem = client.player.getOffHandStack();
							if ((mainItem == null || ! mainItem.isOf(Items.FILLED_MAP)) && (offItem == null || ! offItem.isOf(Items.FILLED_MAP))){
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §cYou are not holding a treasure map"));
							} else if ((mainItem.get(DataComponentTypes.MAP_DECORATIONS) == null || mainItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray().length == 0) && (offItem.get(DataComponentTypes.MAP_DECORATIONS) == null || offItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray().length == 0)) {
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §cYou are holding a map, but it's not a treasure map"));
							} else if (mainItem.get(DataComponentTypes.MAP_DECORATIONS) != null && mainItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray().length != 0 && offItem.get(DataComponentTypes.MAP_DECORATIONS) != null && offItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray().length != 0){
								MapDecorationsComponent.Decoration decoration = (MapDecorationsComponent.Decoration) mainItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray()[0];
								MapDecorationsComponent.Decoration decoration2 = (MapDecorationsComponent.Decoration) offItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray()[0];
								int x = (int) decoration.x();
								int z = (int) decoration.z();
								int x2 = (int) decoration2.x();
								int z2 = (int) decoration2.z();
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §fMain hand: §aX: "+x+" §8| §aZ: "+z+" §8| §fOff hand: §aX: "+x2+" §8| §aZ: "+z2).setStyle(Style.EMPTY.withClickEvent(new ClickEvent.CopyToClipboard("Main hand: X: "+x+" Z: "+z+" | Off hand: X: "+x2+" Z: "+z2)).withHoverEvent(new HoverEvent.ShowText(Text.translatable("chat.copy.click").setStyle(Style.EMPTY.withColor(Formatting.GREEN))))));
							} else if (mainItem.get(DataComponentTypes.MAP_DECORATIONS) != null && mainItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray().length != 0){
								MapDecorationsComponent.Decoration decoration = (MapDecorationsComponent.Decoration) mainItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray()[0];
								int x = (int) decoration.x();
								int z = (int) decoration.z();
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §aX: "+x+" §8| §aZ: "+z).setStyle(Style.EMPTY.withClickEvent(new ClickEvent.CopyToClipboard("X: "+x+" Z: "+z)).withHoverEvent(new HoverEvent.ShowText(Text.translatable("chat.copy.click").setStyle(Style.EMPTY.withColor(Formatting.GREEN))))));
							} else if (offItem.get(DataComponentTypes.MAP_DECORATIONS) != null && offItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray().length != 0){
								MapDecorationsComponent.Decoration decoration = (MapDecorationsComponent.Decoration) offItem.get(DataComponentTypes.MAP_DECORATIONS).decorations().values().toArray()[0];
								int x = (int) decoration.x();
								int z = (int) decoration.z();
								client.inGameHud.getChatHud().addMessage(Text.literal("§f[§dTreasure Map Coordinates§f] §aX: "+x+" §8| §aZ: "+z).setStyle(Style.EMPTY.withClickEvent(new ClickEvent.CopyToClipboard("X: "+x+" Z: "+z)).withHoverEvent(new HoverEvent.ShowText(Text.translatable("chat.copy.click").setStyle(Style.EMPTY.withColor(Formatting.GREEN))))));
							}
						}
						return 1;
					}));
		});
	}
}