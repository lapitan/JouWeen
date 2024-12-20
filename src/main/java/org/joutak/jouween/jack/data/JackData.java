package org.joutak.jouween.jack.data;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.joutak.jouween.config.JouweenConst;
import org.joutak.jouween.jack.files.JackFileReader;
import org.joutak.jouween.jack.files.JackFileWriter;
import org.joutak.jouween.jack.quests.AbstractQuest;
import org.joutak.jouween.jack.quests.AllQuests;
import org.joutak.jouween.jack.quests.QuestNeedToBeStarted;

import java.util.ArrayList;
import java.util.List;

@Data
public class JackData {

    @Getter
    private static JackData instance;

    @Expose
    @Getter
    @Setter
    public double x = 0;

    @Expose
    @Getter
    @Setter
    public double y = 0;

    @Expose
    @Getter
    @Setter
    public double z = 0;

    @Expose
    @Getter
    @Setter
    public int completedQuests = 0;

    @Expose
    @Getter
    @Setter
    public int neededQuests = 100;

    @Expose
    @Getter
    @Setter
    public boolean isFirstAllBottlesComplete = false;

    @Expose
    @Getter
    public List<JackQuestPlayerData> playerDataList = new ArrayList<>();

    @Expose
    @Getter
    @Setter
    public int helmetX = 0;

    @Expose
    @Getter
    @Setter
    public int helmetY = 0;

    @Expose
    @Getter
    @Setter
    public int helmetZ = 0;

    @Expose
    @Getter
    @Setter
    public int chestplateX = 0;

    @Expose
    @Getter
    @Setter
    public int chestplateY = 0;

    @Expose
    @Getter
    @Setter
    public int chestplateZ = 0;

    @Expose
    @Getter
    @Setter
    public int pantsX = 0;

    @Expose
    @Getter
    @Setter
    public int pantsY = 0;

    @Expose
    @Getter
    @Setter
    public int pantsZ = 0;

    @Expose
    @Getter
    @Setter
    public int bootsX = 0;

    @Expose
    @Getter
    @Setter
    public int bootsY = 0;

    @Expose
    @Getter
    @Setter
    public int bootsZ = 0;

    @Expose
    @Getter
    @Setter
    public int swordX = 0;

    @Expose
    @Getter
    @Setter
    public int swordY = 0;

    @Expose
    @Getter
    @Setter
    public int swordZ = 0;

    @Expose
    @Getter
    @Setter
    public int bowX = 0;

    @Expose
    @Getter
    @Setter
    public int bowY = 0;

    @Expose
    @Getter
    @Setter
    public int bowZ = 0;

    @Expose
    @Getter
    @Setter
    public int arrowsX = 0;

    @Expose
    @Getter
    @Setter
    public int arrowsY = 0;

    @Expose
    @Getter
    @Setter
    public int arrowsZ = 0;

    @Expose
    @Getter
    @Setter
    public int breadX = 0;

    @Expose
    @Getter
    @Setter
    public int breadY = 0;

    @Expose
    @Getter
    @Setter
    public int breadZ = 0;

    public void read() {
        JackData jackData = new JackFileReader(JouweenConst.JACK_FILEPATH).readJack();
        this.x = jackData.x;
        this.y = jackData.y;
        this.z = jackData.z;
        this.completedQuests = jackData.completedQuests;
        this.neededQuests = jackData.neededQuests;
        this.isFirstAllBottlesComplete = jackData.isFirstAllBottlesComplete;
        this.playerDataList = jackData.playerDataList;
        this.helmetX = jackData.helmetX;
        this.helmetY = jackData.helmetY;
        this.helmetZ = jackData.helmetZ;
        this.chestplateX = jackData.chestplateX;
        this.chestplateY = jackData.chestplateY;
        this.chestplateZ = jackData.chestplateZ;
        this.pantsX = jackData.pantsX;
        this.pantsY = jackData.pantsY;
        this.pantsZ = jackData.pantsZ;
        this.bootsX = jackData.bootsX;
        this.bootsY = jackData.bootsY;
        this.bootsZ = jackData.bootsZ;
        this.swordX = jackData.swordX;
        this.swordY = jackData.swordY;
        this.swordZ = jackData.swordZ;
        this.bowX = jackData.bowX;
        this.bowY = jackData.bowY;
        this.bowZ = jackData.bowZ;
        this.arrowsX = jackData.arrowsX;
        this.arrowsY = jackData.arrowsY;
        this.arrowsZ = jackData.arrowsZ;
        this.breadX = jackData.breadX;
        this.breadY = jackData.breadY;
        this.breadZ = jackData.breadZ;
        instance = this;
    }

    public void write() {
        new JackFileWriter(JouweenConst.JACK_FILEPATH).write(this);
    }

    public JackQuestPlayerData addPlayer(Player player) {
        JackQuestPlayerData playerData = new JackQuestPlayerData();
        playerData.setPlayerName(player.getName());
        playerDataList.add(playerData);
        write();
        return playerData;
    }

    public AbstractQuest playerGotQuest(Player player) {
        JackQuestPlayerData jackQuestPlayerData = findJackQuestPlayer(player.getName());

        if (jackQuestPlayerData == null) {
            player.sendMessage("Ты почему-то не можешь взять квест. Скрины lapitaniy на стол");
            throw new RuntimeException("PLAYER " + player.getName() + " CAN'T TAKE QUEST!!! NEED TO HOTFIX!!!!");
        }

        AbstractQuest abstractQuest = AllQuests.getRandomQuest();
        jackQuestPlayerData.setCanTakeQuest(false);
        jackQuestPlayerData.setCurrentQuestId(abstractQuest.getId());
        jackQuestPlayerData.setSomeQuestInfo(abstractQuest.getSomeInfo());

        if (abstractQuest instanceof QuestNeedToBeStarted){
            ((QuestNeedToBeStarted) abstractQuest).startQuest(player);
        }

        playerDataList.replaceAll(it -> {
            if (it.equals(jackQuestPlayerData)) {
                return jackQuestPlayerData;
            }
            return it;
        });

        write();

        return abstractQuest;
    }

    public void playerCompleteQuest(Player player) {
        JackQuestPlayerData jackQuestPlayerData = findJackQuestPlayer(player.getName());

        if (jackQuestPlayerData == null) {
            player.sendMessage("Чета пиздец не так. Ты вроде можешь комплитнуть квест, но почему-то тебя нет в списке. Кто ты и что ты кликнул скриншоты на стол lapitaniy");
            throw new RuntimeException("PLAYER " + player.getName() + " CAN'T COMPLETE QUEST!!! NEED TO HOTFIX!!!!");
        }

        completedQuests += AllQuests.getQuestById(jackQuestPlayerData.getCurrentQuestId()).getReward();

        jackQuestPlayerData.setCanTakeQuest(false);
        jackQuestPlayerData.setCurrentQuestId(0);
        jackQuestPlayerData.setSomeQuestInfo("");

        playerDataList.replaceAll(it -> {
            if (it.equals(jackQuestPlayerData)) {
                return jackQuestPlayerData;
            }
            return it;
        });

        write();

    }

    public void playerDeclineQuest(Player player) {
        JackQuestPlayerData jackQuestPlayerData = findJackQuestPlayer(player.getName());

        if (jackQuestPlayerData == null) {
            player.sendMessage("Чета пиздец не так. Ты вроде можешь отказаться от квеста, но почему-то тебя нет в списке. Кто ты и что ты кликнул скриншоты на стол lapitaniy");
            throw new RuntimeException("PLAYER " + player.getName() + " CAN'T COMPLETE QUEST!!! NEED TO HOTFIX!!!!");
        }

        completedQuests -= AllQuests.getQuestById(jackQuestPlayerData.getCurrentQuestId()).getReward();

        jackQuestPlayerData.setCanTakeQuest(false);
        jackQuestPlayerData.setCurrentQuestId(0);
        jackQuestPlayerData.setSomeQuestInfo("");

        playerDataList.replaceAll(it -> {
            if (it.equals(jackQuestPlayerData)) {
                return jackQuestPlayerData;
            }
            return it;
        });

        write();

    }

    public void switchPlayersCanTakeQuests() {
        read();
        playerDataList.forEach(it -> {
                    if (it.getCurrentQuestId() != 0) {
                        return;
                    }
                    if (!it.isCanTakeQuest()) {
                        it.setCanTakeQuest(true);
                    }
                }
        );
        write();
    }

    public JackQuestPlayerData findJackQuestPlayer(String name){
        return playerDataList.stream().filter(it -> it.getPlayerName().equals(name)).findFirst().orElse(null);
    }

    public void replacePlayer(JackQuestPlayerData jackQuestPlayerData){
        read();
        playerDataList.replaceAll(it -> {
            if (it.equals(jackQuestPlayerData)) {
                return jackQuestPlayerData;
            }
            return it;
        });
        write();
    }

}
