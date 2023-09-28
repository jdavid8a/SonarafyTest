# TestSonatafy

The DiffTool class operates as follows:

The Diff method takes the `previous` and `current` objects as input parameters. In this method, the `recDiff` method is called, which is responsible for recursively analyzing the objects to identify potential differences encountered during the process.

The `recDiff` method receives the following parameters: `prop`, which refers to the property being evaluated, `currentObject`, which is the current object, and `previousObject`, which is the previous object. Once inside this method, it evaluates if the current object and the previous object are different and not null, and if the property is not an empty string. If this condition is met, a difference is added.

Next, it checks if the objects are instances of the List type. In that case, it calls the `processList` method, which is responsible for evaluating whether the object in the list has an "ID" field or any field annotated with `@AuditKey`. If none of these fields is found, an exception is thrown, as requested in the challenge. Then, it checks for differences and calls `recDiff` if necessary. Subsequently, it iterates through the `current` and `previous` objects using streams to identify items that may have been added or removed, and adds them to the list of changes.

If the objects are not instances of List but Maps, the `processMap` method is called, where a recursive analysis is performed, and changes are added with the full path separated by dots ".", as specified in the challenge. Finally, the changes are added to the list.

To complete this challenge, it took me 3 hours to develop the solution and an additional hour to review and refactor the code in order to reduce complexity and make it more understandable.




