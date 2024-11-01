package org.joutak.jouween.jack.quests.BottleQuests;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.joutak.jouween.jack.quests.AbstractQuest;
import org.joutak.jouween.mobs.AllMobTypes;

public class FastZombieBottles extends AbstractQuest {

    private static int BOTTLES_AMOUNT = 5;

    public FastZombieBottles(int id, int weight, int reward) {
        this.id = id;
        this.weight = weight;
        this.reward = reward;
    }

    @Override
    public TextComponent getDescription() {
        return Component.text()
                .append(Component.text("Быстрые ноги по голове не получат, говорили они. А ты дай. Мне нужно, чтобы ты принес мне эссенцию быстрых маленьких гадских зомби в количестве ", NamedTextColor.DARK_AQUA))
                .append(Component.text(BOTTLES_AMOUNT, NamedTextColor.GOLD))
                .append(Component.text(" штук. Догонишь? Докажи, что твои ноги быстрее", NamedTextColor.DARK_AQUA))
                .build();
    }

    @Override
    public boolean checkQuest(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        try {
            return itemStack.getType().equals(Material.GLASS_BOTTLE) &&
                    itemStack.getAmount() >= BOTTLES_AMOUNT &&
                    itemStack.getItemMeta().getCustomModelData() == 52 &&
                    itemStack.getItemMeta().hasLore() &&
                    ((TextComponent) itemStack.getItemMeta().displayName()).content()
                            .contains(AllMobTypes.getCustomMobById(7).getMobName());
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public void completeQuest(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        itemStack.setAmount(itemStack.getAmount()-BOTTLES_AMOUNT);
    }

}
