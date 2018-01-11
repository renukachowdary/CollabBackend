package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.UsersDetails;

public interface FriendDao {
	
List<UsersDetails> suggestedUsersList(String username);
public void addFriendRequest(Friend friend);
List<Friend> pendingRequests(String username);
public void updatePendingRequest(Friend friend);
List<UsersDetails> listofFriends(String username);
List<UsersDetails> listofMutualFriends(String loginId,String suggestedUsername);

}