package com.zerosio.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.zerosio.Main;
import com.zerosio.enums.Rank;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class User {

	private static final String MONGO_URI =
		"mongodb://admin:admin@ac-7z3jdjf-shard-00-00.zzju71x.mongodb.net:27017,ac-7z3jdjf-shard-00-01.zzju71x.mongodb.net:27017,ac-7z3jdjf-shard-00-02.zzju71x.mongodb.net:27017/?ssl=true&replicaSet=atlas-12o29g-shard-0&authSource=admin&retryWrites=true&w=majority&appName=Cluster0";

	private static final String MONGO_DB = "network";

	private static MongoClient mongoClient;
	private static MongoCollection<Document> collection;
	private static final Map<UUID, User> userCache = new ConcurrentHashMap<>();

	private final UUID uuid;
	private final Document data;

	private User(UUID uuid, Document document) {
		this.uuid = uuid;
		this.data = document;
	}

	public static void connect() {
		try {
			ConnectionString connString = new ConnectionString(MONGO_URI);
			MongoClientSettings settings = MongoClientSettings.builder()
										   .applyConnectionString(connString)
										   .build();

			mongoClient = MongoClients.create(settings);
			MongoDatabase database = mongoClient.getDatabase(MONGO_DB);
			collection = database.getCollection("users");

			Bukkit.getLogger().info("[MongoDB] Connected to database: " + MONGO_DB);
		} catch (Exception e) {
			Bukkit.getLogger().severe("[MongoDB] Failed to connect: " + e.getMessage());
		}
	}

	public static void disconnect() {
		try {
			if (mongoClient != null) {
				mongoClient.close();
				Bukkit.getLogger().info("[MongoDB] Disconnected from database");
			}
		} catch (Exception e) {
			Bukkit.getLogger().severe("[MongoDB] Error disconnecting: " + e.getMessage());
		}
	}

	public static boolean exists(UUID uuid) {
		try {
			return collection.find(Filters.eq("uuid", uuid.toString())).first() != null;
		} catch (Exception e) {
			Bukkit.getLogger().severe("[MongoDB] Error checking user: " + e.getMessage());
			return false;
		}
	}

	public void setData(String key, Object value) {
		data.put(key, value);
		saveAsync();
	}

	public void saveAsync() {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(Main.class), this::save);
	}
	
	public void save() {
		try {
			collection.replaceOne(
				Filters.eq("uuid", uuid.toString()),
				data,
				new ReplaceOptions().upsert(true)
			);
		} catch (Exception e) {
			System.err.println("[MongoDB] user data save hoise " + uuid + ": " + e.getMessage());
		}
	}

	public static User getUser(UUID uuid) {
		return userCache.computeIfAbsent(uuid, id -> {
			Document found = collection.find(Filters.eq("uuid", id.toString())).first();
			if (found != null) {
				return new User(id, found);
			}
			return null;
		});
	}

	public String getString(String key) {
		Object val = data.get(key);
		return val != null ? val.toString() : null;
	}

	public boolean getBoolean(String key) {
		Object val = data.get(key);
		return val instanceof Boolean && (Boolean) val;
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) data.get(key);
	}

	public <T extends Enum<T>> T getEnum(String key, Class<T> enumClass, T def) {
		String value = getString(key);
		if (value == null) return def;
		try {
			return Enum.valueOf(enumClass, value.toUpperCase());
		} catch (Exception e) {
			return def;
		}
	}

	public void debug(Player player, String message) {
		if (getBoolean("debug_mode")) {
			player.sendMessage("§c[SYSTEM] §f" + message);
		}
	}

	public Rank getRank() {
		Rank storedRank = getEnum("rank", Rank.class, Rank.DEFAULT);
		Rank packageRank = getEnum("package_rank", Rank.class, Rank.DEFAULT);
		Rank newPackageRank = getEnum("new_package_rank", Rank.class, packageRank);
		Rank monthlyRank = getEnum("monthly_rank", Rank.class, Rank.DEFAULT);

		if (storedRank != null && storedRank.isStaff()) {
			return storedRank;
		}

		long currentTime = System.currentTimeMillis();
		Object monthlyBoughtTimeObj = data.get("monthly_rank_bought_time");
		long monthlyBoughtTime = monthlyBoughtTimeObj instanceof Number ?
								 ((Number) monthlyBoughtTimeObj).longValue() : -1L;

		if (monthlyRank != null && monthlyBoughtTime > 0 &&
				(currentTime - monthlyBoughtTime <= 2592000000L)) {
			return monthlyRank;
		}

		return newPackageRank != null ? newPackageRank : Rank.DEFAULT;
	}
	
	public void setMonthlyRank(Rank rank) {
		setData("monthly_rank", rank.name());
		setData("monthly_rank_bought_time", System.currentTimeMillis());
	}

	public static String retrieveLastKnownName(UUID uuid) {
		User user = getUser(uuid);
		return user != null ? user.getString("last_known_name") : null;
	}

	public String retrieveLastKnownName() {
		return getString("last_known_name");
	}
}
