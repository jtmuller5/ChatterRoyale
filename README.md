# ChatterRoyale
 Social media app to determine top post of each day
# Round


## Definition

Each round is a 24 hour period wherein users can submit anonymous posts to a shared feed. Each round is divided into 12 two hour stages that define how the program operates for the given time range. Users will compete with the primary goal of submitting the top post for the day. For now, the top post from each day will be saved in a public winner’s feed. Posts submitted by each user will be saved to their profiles with a post ranking based on how many other posts were remaining when they were eliminated. The key is that the current ranking of each entry will not be revealed to other players until the end of the round. Players will be able to see statistics about their own post (upvotes, downvotes, current rank, etc.) but will not get to see these for others. User’s do NOT have to submit an entry in order to vote on the feed.


## Attributes



*   **Round ID **- The first round ever will be recorded as round 1 and each sequential round will increment this value by 1. This value will serve as a unique identifier for the round that will be referenced by the associated stages so everything stays connected.
*   **Round date** - The calendar date that the round is occurring on
*   **Current stage** - This holds the number of the current stage
*   **Winning entry** - Once the round has ended, the winning entry will be saved here
*   **Total entries **- Once the round has ended, the total number of entries that were live will be recorded


# Stage


## Definition

Each stage of a round will determine what user interaction is allowed for a specific time period. Each round will be hard coded to last 2 hours and will have metrics that determine how many entries are allowed by the end of the stage. The current stage will be displayed on the main feed with hover functionality that informs the users what actions are allowed during the stage. The characteristics of the stage will be determined pseudo randomly with some restrictions in place. In order to get to the maximum number of surviving entries by the stage’s end, entries will be eliminated on a rolling basis based on which entry has the fewest number of value points. If there is a tie between two or more entries, preference is given to the oldest entry. Posts will be eliminated until the max number is reached.


## Attributes



*   **Stage number** - The stage number (between 1 and 12)
*   **Round ID** - The round ID that this stage belongs to
*   **Start time** - The clock time that determines when the stage starts
*   **End time **- The clock time that determines when the stage ends
*   **Maximum surviving entries** - The maximum number of surviving entries that are allowed by the end of the stage. This value is dynamically generated based on the number of surviving entries from the previous round and the number of remaining stages. In order to get to this value before the stage time has expired, the stage will be divided into 12 ten minute spans with their own dynamically generated maximum surviving entry values.
*   **Remaining stages **- The number of stages left before the round is over (12 - Current stage number)
*   **Entries eliminated** - The total number of entries eliminated during between the start and end of this stage
*   **New entry added VPs** - New entries submitted during this stage will start with this number of additional value points. The idea here is that entries submitted earlier in the round will be seen by more users and will have had the chance to be upvoted/downvoted more. This value will be generated dynamically based on the status of existing entries and will be displayed publicly on the feed.
*   **Starting entries** - The number of entries the stage starts with
*   **Active users **- A list of users with an active post during the stage
*   **Purchase Stage** - Indicates if users can make purchases during this stage


## Characteristics

This is a list of the possible characteristics a stage can have. These characteristics are used to change the user experience.



*   **No Purchases** - Users cannot use EXP gained during the stage to purchase tags
*   **Visible Tags** - All tag values for posts are publicly visible
*   **Visible Votes** - All upvotes and downvotes for posts are publicly visible (normally this would be used for early stages only)
*   **Long Sort/High Sort** - Entries are either sorted based on the time they have existed or the number of VPs they have
*   **Visible Levels** - The levels for each user are displayed by their posts


# Entry


## Definition

An entry consists of a single user’ submission to a round. Each user may only have one active entry per round but they can submit multiple entries in the case that their original entry is eliminated or they abandon it. Initially, an entry will simply be a text post by the user that contains a witty/punny thought or question. Entries will have a 100 character limit, based on the fact that only [9% of Twitter users were hitting the 140 character limit.](https://techcrunch.com/2018/10/30/twitters-doubling-of-character-count-from-140-to-280-had-little-impact-on-length-of-tweets/) Entries should be limited enough to make user’s be thoughtful about what they are writing.


## Attributes



*   **Entry ID **- The unique entry ID (the first entry ever having a value of 1)
*   **Round ID** - The ID of the round the entry was submitted to
*   **Stage number** - The stage number the entry was submitted to
*   **Submission time** - The clock time the entry was submitted (everything else can be determined from the round and stage)
*   **Time Alive** - A running timer that indicates how long the entry has been in play
*   **Alive?** - A flag that indicates if the entry is still in play. This field will be used to populate the main feed
*   **Elimination time** - The clock time the entry was eliminated, either by DE or stage elimination (SE)
*   **Rank/Total** - The final placement of the entry over the total number of entries for the round
*   **User ID** - The ID of the user who submitted the entry
*   **Post **- The text that is included in the entry
*   **Characters **- The number of characters included in the entry
*   **Upvotes **- The number of upvotes this entry has received
*   **Downvotes **- The number of downvotes this entry has received
*   **Upvoting users** - A list of user IDs that upvoted this entry
*   **Downvoting users** - A list of user IDs that downvoted this entry (used to determine EXP given for “Deserved Eliminations”)
*   **Value points (VP)** - Each entry will start with 0 VPs and this will hold the current number of VPs. Upvotes will add to the number of VPs while downvotes will subtract. VPs will be used to determine when users are eliminated at the end of each round.
*   **Effective VP change** - In order to prevent users who join later in the round from posting entries that cannot easily be downvoted to 0 VPs, we will track the cumulative VP change for a post. The starting VP value will serve as 0. If an entry is downvoted to -10 VPs it will be removed from the round and this is considered a “Deserved Elimination”. Deserved Eliminations help to distinguish competitive downvoting from downvoting for the sake of removing inappropriate content.
*   **Stage 1-12 VP changes** - The total number of VPs added or removed for each stage. This field will store the current VP change for the current stage
*   **Stage 1-12 VP start** - The number of VP the entry started each stage with
*   **Stage 1-12 VP end** - The number of VP the entry ended each stage with
*   **Tags **- This will contain a list of tags that other users have added to the entry. Different tags will have different effects on the receiving post


# User 


## Definition

Each user of the app will have a unique profile that stores their performance data, experience points, and perks. All of the user’s historical posts and settings will also be associated with a user record.


## Attributes



*   **User ID** - A unique number ID used to identify the use. The first user to create a profile will be assigned the unique ID of 1, and every sequential user will increment this value.
*   **Username **- The username entered by the user that is visible on their profile. If the user chooses, other users can access their profile and view their historical entries
*   **Creation date** - The calendar date when this user was created
*   **Creaton time** - The clock time when this user was created
*   **Total rounds entered** - A count of the number of rounds the user has submitted an entry to. There is only one round per 24-hour period
*   **Total entries submitted** - A count of the number of entries the user has submitted
*   **Past Entries **- A list of all past entries submitted by the user
*   **Total round wins** - The number of rounds the user has won
*   **Experience points (EXP) **- This is a running count of the number of experience points the user has accumulated. EXP are gained by upvoting entries, downvoting entries, eliminating entries, tagging entries, and otherwise interacting with the round’s feed. EXP are also awarded based on entry performance
*   Level - The experience level of the user as determined by the 
*   **Total entries eliminated** - The total number of posts the user has eliminated
*   **Tag totals** - For each tag in existence, the user record will contain a running tally of how many they have received
*   **Spendable EXP (Pocket) **- The current number of EXP the user can spend. EXP for a stage is separated into spendable and spent EXP. Even if EXP is spent, it is still added to the user’s total EXP count. EXP that is not spent at the end of each round is added to the Spendable EXP (Stash) total. The stash EXP can be spent in the perk store on perks that affect a player’s performance.
*   **Spendable EXP (Stash) **- EXP not spent during a round, either because the stage expired or because there were no affordable tags that the user wanted, is added to the Spendable EXP Stash. This EXP can be spent on big ticket perks in the perk store. The idea here is to give users who cannot interact with the feed as much the ability to benefit in the long run.


## Actions

During a round, each user can take a number of actions.



*   **Upvote **- A user can upvote each entry in the feed one time. Upvoting an entry adds 1 VP to the entry, awards the entry’s user 1 EXP, and awards the upvoting user 3 EXP. During each stage, each user will be allowed a limited amount of upvotes based on the number of entries the stage begins with (starting entries * 75%). Upvotes cannot be undone.
*   **Downvote **- A user can downvote each entry in the feed one time. Downvoting an entry subtracts 1 VP from the entry, subtracts 1 EXP from the entry’s user, and awards the downvoting user 1 EXP. If the entry is eventually eliminated via a “Deserved Elimination”, all downvoting users will receive an additional 4 EXP. If a user delivers the final downvote on an entry, the downvoting user will receive 5 EXP and the entry user will lose 3 EXP. During each stage, each user will be allowed a limited amount of downvotes based on the number of entries the stage begins with (starting entries * 25%). Downvotes cannot be undone.
*   **Tag **- Each user will have a finite number of tags per round that can be awarded to posts. Each tag will define its own effects (such as EXP gained, VPs added, special effects)
*   **Edit **- Users may edit their own entry if they use an “Edit” tag
*   **Abandon **- A user can abandon their current entry in order to submit another entry. Note that only one entry can be submitted per stage. A user may abandon their entry if they think another entry may perform better or to strategically benefit from the stage’s VP bonus.
*   **Heal **- A user can trade EXP gained from the current stage for VPs. The starting rate is 10 EXP for 1 VP
*   **Purchase **- A user can use the spendable EXP they have gained during the current stage to purchase tags in the tag store. EXP that is spent in the tag store is still added to the user’s total EXP count.


# Experience Points


## Definition

Experience points act as an incentive for users to interact with the round’s feed. The amount of EXP awarded for each action should be tailored so that users are encouraged to treat other player’s favorably (rather than downvote for the sake of downvoting). EXP will be used to level players up and can be spent on perks and customizations in the perk store.


## Rewarded Actions

Actions defined here are basic actions a user may take to interact with the rounds feed and should not be rare occurrences.



*   **Upvoting**: 3 EXP
*   **Being upvoted**: 1 EXP
*   **Downvoting**: 1 EXP
*   **Downvoting a post that is eventually removed via a “Deserved Elimination” **- 4 EXP (on top of the original 1 EXP for downvoting) 
*   **Eliminating a post**: 5 EXP
*   **Being downvoted**: -1EXP
*   **Being eliminated via a “Deserved Elimination”**: -3EXP
*   **Tagging a post**: 2 EXP


## Rewarded Accomplishments

Accomplishments defined here are typically rare occurrences that will result in greater EXP rewards.



*   **Winning a round**: Dynamically determined amount based on total number of round entries
*   **Winning a stage**: 10 EXP for having the greatest VP change for a stage
*   **Survivor**: 20 EXP for surviving a round where your entry’s VPs are below 10 the entire time


# Tag


## Definition

Tags are used to indicate that an entry has a specific characteristic or makes a specific impression. Tags may be used to point out an entry’s wittiness, stellar vocabulary, or originality. Tags help user’s elaborate why they enjoy or dislike an entry and many will have follow up questions that force the user to quickly explain why the tag is relevant or where the tag should be attached (ex. A specific word). Tag records will be user/entry agnostic. The use of these tags will be recorded in user and entry records.


## Attributes



*   **Tag user EXP reward** - The number of EXP the tag user will be rewarded for attributing the tag to an entry
*   **Tag recipient EXP reward** - The number of EXP the tag receiver will be rewarded (can be negative)
*   **VP Change** - The number of VPs the tag adds or subtracts from the post
*   **Tag Color** - The color of the tag visible in the feed
*   **Tag Name** - A descriptive name that describes what the tag is indicating in the receiving post
*   **Tag Description** - A longer text entry explaining what this tag is used for and what its effects are
*   **Tag Type **- Determines if the tag defines a characteristic of a post or performs an action on the post (Characterisic vs Action)
*   **Sticky? **- Determines if the tag can be moved from one entry to another or if it sticks to the first entry
*   **Self Care?** - Determines if the tag can be added to the user’s own post
*   **Lifetime **- Determines how long the tag remains after it is put into play. Typically this field would contain a count of the number of stages or a literal time value
*   **Price **- The number of EXP required to purchase this tag during a round


# FAQ


## How are entries sorted?

In normal social media feeds, the most popular posts gravitate towards the top of a feed and stay there, benefiting from increased exposure. In Chatter Royale, the aim is to reduce the impact of momentum as much as possible while still providing a valuable user experience. For this reason, entries will be sorted so that the most upvoted entries appear at the top of the feed. Users can also tag their entries to be in the spotlight which will move these posts to the top of the feed temporarily before they are sorted appropriately. New posts will receive a short spotlight so they are not buried. Spotlight tags can also be purchased from during each stage that allows purchases, with longer spotlights costing more EXP. If there is a tie between the upvotes of two entries, the entry that has been around the shortest amount of time will appear first (since it has a greater upvote density).


## How many entries can be added each stage?

The main goal of Chatter Royale is to end each 24 hour round with a single post that represents the most popular thought or fact of the day. In order to do this, entries must be eliminated at a rate that exceeds the rate they are being created. On the same note, the app is meant to be interactive in the way that users can be entertained regardless of whether they join the round at 6am or 6pm. Consequently, any number of entries can be added during each stage. To counterbalance this possibility, a reduction period exists at the end of each stage wherein no new entries can be added. During this reduction period, the existing entries will be whittled down to the maximum entry number specified for the given stage. The reduction period will be segmented into minute-long intervals. For each interval where the number of existing entries exceeds the maximum number of entries allowed, entries will be eliminated if they do not have a minimum number of VPs. If enough entries are eliminated before the reduction period has ended, all remaining entries are considered to be safe.


## How many entries are deleted each stage?

The number of entries that are deleted each stage will vary. For now, each stage will have a maximum number of entries that are allowed to be alive by the END of the stage. These maximums will represent 


## How long is the reduction period?


## How can new entries compete with existing entries?

Rather than having each new entry always start at 0 VP, the starting VPs for each new post will be dynamically determined at the beginning of each stage after stage 1. Existing entries will still have a significant advantage over new entries but the handicap offered each round will give new posts the opportunity to catch up.


## If new entries start with more than 0 VPs, is it harder for them to be eliminated via DE?

Each entry has a value point field, which determines when a post will be eliminated by pool reduction, and an effective value point field, which determines the cumulative VP change that a post has experienced since it was created. If the effective value point field falls to -10, the post will still be eliminated via DE.


## When is data submitted from the client to the database?

Scores are not displayed on the main feed so it is not essential for upvotes and downvotes to be submitted immediately. To save on Google Firestore costs, it would be most cost effective to submit the cumulative input from each user to the database at the end of each round (if there was activity during the round). Worst case scenario, a user that participated in all 12 stages would make a maximum of 12 writes to the database. We can only place this limitation on database writes because doing the same for database reads would subtract from the user experience. New posts still need to be displayed as soon as they are posted and posts with inappropriate content need to be eliminated after 10 downvotes. 


# Appendix


#### Deserved elimination

An elimination that occurs when an entry has cumulatively received 10 downvotes. In other words, its effective value point count is -10.


#### Pool Reduction

The process of reducing the number of surviving entries by eliminating entries that do not have a given amount of VPs

