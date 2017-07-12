# RoomDatabaseDemo
- This demo uses the latest Google Android Architecure libraries including `LiveData`, `Room`, `ViewModel`. The overall architecture is MVVM.
- One of the advantanges of this demo is we can keep the Activities(or Fragments) clean and tidy. The `ViewModel` can help save the data automatically when system configuration is changed.
- Moreover, the `Room`, a ORM library, works very well with `ViewModel` and `DataBinding` library.
- The last but not least, LiveData objects will notify their observers to update views when their content changed.
