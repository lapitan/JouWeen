package org.joutak.jouween.boss;

import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.Getter;
import org.joutak.jouween.JouWeen;
import org.joutak.jouween.config.JouWeenConfig;
import org.joutak.jouween.config.JouweenConst;
import org.joutak.jouween.jack.files.JackFileReader;
import org.joutak.jouween.jack.files.JackFileWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class JackBossData {

    @Getter
    private static JackBossData instance;

    @Expose
    public boolean bossStarted = false;

    @Expose
    public XYZLocation bossLocation = new XYZLocation();

    @Expose
    public String bossWorldName = "world";

    @Expose
    public List<XYZLocation> bossSummonLocations = List.of();

    @Expose
    public List<XYZLocation> mobSpawnLocations = List.of();

    @Expose
    public XYZLocation portalLocation = new XYZLocation();

    @Expose
    public XYZLocation endLocation = new XYZLocation();

    public static void createJackBossData(){
        if (instance == null){
            instance = new JackBossData();
        }
    }

    public void write() {
        new JackFileWriter(JouweenConst.BOSS_FILEPATH).writeJackBoss(this);
    }

    public void read() {
        instance = new JackFileReader(JouweenConst.BOSS_FILEPATH).readJackBoss();
    }

    public void addBossSummonLocation(XYZLocation location){
        bossSummonLocations.add(location);
        write();
    }

    public void addMobSpawnLocations(XYZLocation location){
        mobSpawnLocations.add(location);
        write();
    }

}
