package com.encapsulation;

import com.mybean.UserModel;

import java.util.HashMap;
import java.util.Map;

public class LMSCache {
    private Map<String, Map<String, Map<String, UserModel>>> cache;
    public LMSCache() {
        // Initialize the cache
        cache = new HashMap<>();
    }

    // Method to add user data to the cache
    public void addToCache(String lmsNode, String usersNode, String userId, UserModel userModel) {
        // Check if LMS Node exists in the cache
        if (!cache.containsKey(lmsNode)) {
            // If not, create a new entry for LMS Node
            cache.put(lmsNode, new HashMap<>());
        }

        // Get the map for USERS Node under the specified LMS Node
        Map<String, Map<String, UserModel>> usersNodeMap = cache.get(lmsNode);

        // Check if USERS Node exists in the cache
        if (!usersNodeMap.containsKey(usersNode)) {
            // If not, create a new entry for USERS Node
            usersNodeMap.put(usersNode, new HashMap<>());
        }

        // Add UserData to the cache under the specified USERID
        usersNodeMap.get(usersNode).put(userId, userModel);
    }
    // Method to retrieve user data from the cache
    public UserModel getUserData(String lmsNode, String usersNode, String userId) {
        // Check if LMS Node exists in the cache
        if (cache.containsKey(lmsNode)) {
            // Get the map for USERS Node under the specified LMS Node
            Map<String, Map<String, UserModel>> usersNodeMap = cache.get(lmsNode);

            // Check if USERS Node exists in the cache
            if (usersNodeMap.containsKey(usersNode)) {
                // Get the map for USERID under the specified USERS Node
                Map<String, UserModel> userIdMap = usersNodeMap.get(usersNode);

                // Check if USERID exists in the cache
                if (userIdMap.containsKey(userId)) {
                    // Return the UserData associated with the specified USERID
                    return userIdMap.get(userId);
                }
            }
        }

        // Return null if user data is not found in the cache
        return null;
    }
}
