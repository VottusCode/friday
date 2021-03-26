package cz.maku.friday.storage.sql;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.maku.friday.Friday;
import cz.maku.friday.util.Logger;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SQLStorage {

    private final String table;
    private final Logger logger;

    public SQLStorage(String table) {
        this.table = table;
        logger = Friday.getPlugin().getLog();
    }

    /**
     * @param cols
     * @author ZorTik
     */
    public void createTable(Map<String, Class<?>> cols) {
        String colsString = null;

        for (String name : cols.keySet()) {
            Class<?> type = cols.get(name);
            String addon = name;
            String var8 = type.getSimpleName().toLowerCase();
            byte var9 = -1;
            switch (var8.hashCode()) {
                case -891985903:
                    if (var8.equals("string")) {
                        var9 = 3;
                    }
                    break;
                case 3355:
                    if (var8.equals("id")) {
                        var9 = 1;
                    }
                    break;
                case 55126294:
                    if (var8.equals("timestamp")) {
                        var9 = 5;
                    }
                case 106079:
                    if (var8.equals("key")) {
                        var9 = 0;
                    }
                    break;
                case 3076010:
                    if (var8.equals("data")) {
                        var9 = 2;
                    }
                    break;
                case 1958052158:
                    if (var8.equals("integer")) {
                        var9 = 4;
                    }
            }

            switch (var9) {
                case 0, 1 -> addon = name + " int NOT NULL primary key AUTO_INCREMENT";
                case 2 -> addon = name + " mediumtext";
                case 3 -> addon = name + " varchar(512)";
                case 4 -> addon = name + " int";
                case 5 -> addon = name + " TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP";
            }

            if (colsString == null) {
                colsString = "(" + addon;
            } else {
                colsString = colsString + ", " + addon;
            }
        }

        colsString = colsString + ")";

        String finalColsString = colsString;
        Bukkit.getScheduler().runTaskAsynchronously(Friday.getPlugin(), () -> {
            try {
                PreparedStatement st = Friday.getPlugin().getDataSource().con.prepareStatement("CREATE TABLE " + table + " " + finalColsString + ";");
                st.executeUpdate();
                st.close();
                logger.log(Logger.LoggerType.SUCCESS, "Table " + table + " was created.", true);
            } catch (Exception var10) {
                if (!var10.getMessage().toLowerCase().contains("exists")) {
                    var10.printStackTrace();
                    logger.log(Logger.LoggerType.ERROR, "There is an exception while creating table " + table + ". Check console.", false);
                }
            }
        });
    }

    public Connection connection() {
        return Friday.getPlugin().getDataSource().con;
    }

    public List<String> selectList(String where, String whereKey, String finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?;");
            st.setString(1, whereKey);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                List<String> list = Lists.newArrayList();
                list.add(rs.getString(finalKey));
                return list;
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String selectString(String where, String whereKey, String finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?;");
            st.setString(1, whereKey);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getString(finalKey);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cDošlo k chybě.";
    }

    public double selectDouble(String where, String whereKey, String finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?;");
            st.setString(1, whereKey);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getDouble(finalKey);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int selectInteger(String where, String whereKey, String finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("SELECT * FROM " + table + " WHERE " + where + " = ?;");
            st.setString(1, whereKey);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt(finalKey);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Map<String, String> selectTop(String where, String value, String finalKey, String top) {
        try {
            PreparedStatement st = connection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` > '0' ORDER BY `" + finalKey + "` DESC LIMIT " + Integer.parseInt(top) + "");
            ResultSet rs = st.executeQuery();
            Map<String, String> map2 = Maps.newHashMap();
            while (rs.next()) {
                map2.put(rs.getString(value), rs.getString(finalKey));
            }
            st.close();
            rs.close();
            return map2;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int selectMaxInteger(String where, String whereKey, String finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("SELECT MAX(" + finalKey + ") FROM " + table + " WHERE " + where + " = ?;");
            st.setString(1, whereKey);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("MAX(" + finalKey + ")");
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void update(String where, String whereKey, String column, String finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("UPDATE " + table + " SET " + column + " = ? WHERE " + where + " = ?;");
            st.setString(1, finalKey);
            st.setString(2, whereKey);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String where, String whereKey, String column, double finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("UPDATE " + table + " SET " + column + " = ? WHERE " + where + " = ?;");
            st.setDouble(1, finalKey);
            st.setString(2, whereKey);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(String where, String whereKey, String column, int finalKey) {
        try {
            PreparedStatement st = connection().prepareStatement("UPDATE " + table + " SET " + column + " = ? WHERE " + where + " = ?;");
            st.setInt(1, finalKey);
            st.setString(2, whereKey);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Insert... builders) {
        String sql = "";
        Insert[] var3 = builders;
        int var4 = builders.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Insert b = var3[var5];
            String sqldata = "";
            int i = 0;
            String[] var9 = b.getData();
            int var10 = var9.length;

            for (int var11 = 0; var11 < var10; ++var11) {
                String d = var9[var11];
                sqldata = sqldata + "'" + d + "'";
                ++i;
                if (i != b.getData().length) {
                    sqldata = sqldata + ", ";
                }
            }

            sql = sql + "INSERT INTO " + table + " (" + b.getColumns() + ") VALUES (" + sqldata + "); ";
        }

        try {
            PreparedStatement st = connection().prepareStatement(sql.replace("'-1'", "NULL"));
            st.executeUpdate();
            st.close();

        } catch (SQLException var20) {
            var20.printStackTrace();
        }

    }

    public boolean isTableExist() {
        boolean tExists = false;
        try (ResultSet rs = connection().getMetaData().getTables(null, null, table, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(table)) {
                    tExists = true;
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tExists;
    }
}
