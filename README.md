Base structure for android project Stack:
  - RxJava3 (https://github.com/ReactiveX/RxJava)
  - LiveData
  - ViewModel
  - retrofit2 (https://github.com/square/retrofit)
  - Room
  - FontAwesome (for icon) (https://github.com/mikepenz/Android-Iconics)
  - Gson (https://github.com/google/gson)
  - Paging3

Architecture of an android project + example of the stacks used.

Flowable + Room + PagingData : list of users in the db + pagination for display. Each time the table is updated, the observer is triggered. <br/>
Flowable + Room + LiveDataReactiveStreams: updated count of the user table. Each time the table is updated, the observer is triggered. <br/>
Single + Retrofit : Data retrieval from the network (api, etc.) <br/>
And more...
