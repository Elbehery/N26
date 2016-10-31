This service is working as descriped in the requirement. 


*********************************** Asymptotic Analysis *************************************

The service relies on Map<k,V> for holding the transaction in-memory. 

The time complexity of the Map<K,V> is O(1) constant time. In fact it's Contstant time Amortized, because the resizing of the backed Array consume O(n)
Linear Time to copy the old entried into the new Array. Though, the linear time is not frequent, and it's amortized to be constant in average. 

I have a used a special implmentation for Map<K,V> which is ConcurrentHashMap. This very implementation allow concurrent modification for the Map<k,V>
entries, by stripping the backed Array into separate layers, and locking only the layer in which a current writing is taking place.

Moreover, the concurrentHashMap allows reading to take place without contstraints, even while writing is in progress, because Reading operations does 
not change the consistency of the data in the Map.


*********************************       NOTICE *******************************************************

I have created the DAO instance as Singleton, instead of using the @Injecet. I have some problems with my configurations, and the Injection of Jersey 
is not working properly. I made this work around for submission, though I am aware it's not the best solution.


