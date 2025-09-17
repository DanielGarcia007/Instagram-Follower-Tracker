import instaloader
import pandas as pd
import sys

#Daniel Garcia
#This program pulls the data from instagram using python

username = sys.argv[1]
password = sys.argv[2]

bot = instaloader.Instaloader()
bot.login(user=username, passwd=password)

# Loading a profile from an Instagram account
profile = instaloader.Profile.from_username(bot.context, username)

# Retrieving the usernames of all followers
followers = [follower.username for follower in profile.get_followers()]

# Converting the data to a DataFrame
followers_df = pd.DataFrame(followers)

# Storing the results in a CSV file
followers_df.to_csv('followers.csv', index=False)

# Getting the usernames of all followings
followings = [followee.username for followee in profile.get_followees()]

# Converting the data to a DataFrame
followings_df = pd.DataFrame(followings)

# Storing the results in a CSV file
followings_df.to_csv('followings.csv', index=False)
