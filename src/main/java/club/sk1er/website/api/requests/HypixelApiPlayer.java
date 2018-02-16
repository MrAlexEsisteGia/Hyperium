package club.sk1er.website.api.requests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hcc.utils.JsonHolder;
import net.hypixel.api.GameType;
import net.hypixel.api.util.ILeveling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mitchellkatz on 3/3/17.
 */
public class HypixelApiPlayer implements HypixelApiObject {

    public static final DateFormat DMY = new SimpleDateFormat("dd/MM, YYYY");
    public static final DateFormat DMYHHMMSS = new SimpleDateFormat("dd/MM, YYYY HH:mm:ss");

    private JsonHolder player;


    public HypixelApiPlayer(JsonHolder holder) {
        this.player = holder;
    }


    public double getTotalXP() {
        return ILeveling.getTotalExpToLevel(getRoot().optInt("oldLevel") + 1) + getRoot().optInt(ILeveling.EXP_FIELD);
    }


    public int getKarma() {
        return getRoot().optInt("karma");
    }

    public long getFirstLoginLong() {
        return getRoot().optLong("firstLogin");
    }

    public long getLatestLoginLong() {
        return getRoot().optLong("lastLogin");
    }

    public String getLastLoginDate() {
        return DMY.format(new Date(getLatestLoginLong()));
    }

    public String getFirstLoginDate() {
        return DMY.format(new Date(getFirstLoginLong()));
    }

    public int getNetworkLevel() {
        return getRoot().optInt("networkLevel") + 1;
    }

    public boolean isValid() {
        return player != null && !player.isNull("player");
    }

    @Override
    public JsonObject getData() {
        return player.getObject();
    }

    public JsonArray getAliases() {
        return getRoot().optJSONArray("knownAliases");
    }

    public String getUUID() {
        return getRoot().optString("uuid");
    }

    public JsonHolder getRoot() {
        return player.optJSONObject("player");
    }

    public JsonHolder getStats() {
        return getRoot().optJSONObject("stats");
    }

    public JsonHolder getStats(GameType type) {
        return getStats().optJSONObject(type.getDbName());
    }

    public String getName() {
        return getRoot().optString("displayname");
    }

    public JsonHolder getGiftMeta() {
        return getRoot().optJSONObject("giftingMeta");
    }

    public JsonHolder getVotingMeta() {
        return getRoot().optJSONObject("voting");
    }


    public int getAchievementPoints() {
        return getRoot().optInt("points");
    }


    public int getTotalCoins() {
        return getRoot().optInt("coins");
    }

    public int getTotalQuests() {
        return getRoot().optInt("questNumber");
    }

    public int getTotalKills() {
        return getRoot().optInt("kills");
    }

    public int getTotalWins() {
        return getRoot().optInt("wins");
    }

    public boolean has(String val) {
        return getRoot().has(val);
    }

    public GameType mostRecentGame() {
        return GameType.parse(getRoot().optString("mostRecentGameType"));
    }

    public boolean isYouTuber() {
        return getRoot().optString("rank").equalsIgnoreCase("youtuber");
    }


    public boolean isStaffOrYT() {
        return isStaff() || isYouTuber();
    }

    public boolean isStaff() {
        String rank = getRoot().optString("rank");
        return rank.equalsIgnoreCase("admin") || rank.equalsIgnoreCase("moderator") || rank.equalsIgnoreCase("helper");
    }

    public long getLastLoginLong() {
        return getRoot().optLong("lastLogin");
    }

    public long getLastLogoffLong() {
        return getRoot().optLong("lastLogout", System.currentTimeMillis());
    }

    public long getCacheTime() {
        return player.optLong("cache");
    }

    public boolean isInvalidPlayer() {
        return player.optString("cause").equalsIgnoreCase("non-player");
    }

    public String getRankForMod() {
        if (isStaff() || isYouTuber()) {
            String string = getRoot().optString("rank");
            if (!string.equalsIgnoreCase("normal"))
                return string;
        } else if (getRoot().has("newPackageRank"))
            return getRoot().optString("newPackageRank");
        else if (getRoot().has("packageRank"))
            return getRoot().optString("packageRank");
        return "NONE";
    }


    public boolean isLoaded() {
        return !player.getKeys().isEmpty();
    }

    public String getDisplayString() {
        return getRoot().optString("display");
    }
}
