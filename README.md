# spark-tutorial
Examples for Apache Spark

A spark application consists of 2 kinds of processes:
1. driver process
2. executor process

**Driver process**
1. Maintains information about the spark application
2. Responds to user's program or input
3. Analyzes, schedules and distributes the work across executors

**Executor process**
1. Execute code assigned by the driver
2. Report state of computation back to driver


## Spark session
- You can control the Spark Application through a driver process called the **Spark session**.
-  The SparkSession instance is the way Spark executes user-defined manipulations across the cluster. 
- There is a one-to-one correspondence between a SparkSession and a Spark Application.

## Dataframe

- A DataFrame is the most common Structured API and simply represents a table of data with rows and columns. 
- The list that defines the columns and the types within those columns is called the schema.
- DataFrame can span thousands of computers. 
- It’s quite easy to convert Pandas (Python) DataFrames to Spark DataFrames, and R DataFrames to Spark DataFrames.

## Partitions

- To allow every executor to perform work in parallel, Spark breaks up the data into chunks called partitions. 
- A partition is a collection of rows that sit on one physical machine in your cluster. 
- A DataFrame’s partitions represent how the data is physically distributed across the cluster of machines during execution.
- You do not (for the most part) manipulate partitions manually or individually. You simply specify high-level transformations of data in the physical partitions, and Spark determines how this work will actually execute on the cluster.

## Transformations
- In Spark, the core data structures are immutable, meaning they cannot be changed after they’re created. To “change” a DataFrame, you need to instruct Spark how you would like to modify it to do what you want. These instructions are called transformations.
- There are two types of transformations:
    - **Narrow transformations** - Transformations consisting of narrow dependencies are those for which each input partition will contribute to only one output partition.
    - **Wide transformations** - A wide dependency (or wide transformation) style transformation will have input partitions contributing to many output partitions. You will often hear this referred to as a shuffle whereby Spark will exchange partitions across the cluster.

## Lazy evaluation
- Lazy evaulation means that Spark will wait until the very last moment to execute the graph of computation instructions.
- In Spark, instead of modifying the data immediately when you express some operation, you build up a plan of transformations that you would like to apply to your source data. By waiting until the last minute to execute the code, Spark compiles this plan from your raw DataFrame transformations to a streamlined physical plan that will run as efficiently as possible across the cluster.

## Actions
- Actions are the operations that return a final value to the driver program or persist data to an external storage.
- To trigger the computation, we run an action.
- An action instructs Spark to compute a result from a series of transformations.
- There are 3 kinds of actions:
    - Actions to view data in the console
    - Actions to collect data to native objects in the respective language
    - Actions to write to output data sources

Examples of actions:
1. collect
2. count
3. countByValue
4. take
5. saveAsTextFile
6. reduce

**collect**

- Collect operation retrieves the entire RDD and returns it to the driver program in the form of regular collection or value. e.g.- If yu have a String RDD, you will get a list of Strings.
- This is quite useful if the spark program has filtered RDD down to a relatively smaller size and you want to deal with it locally.
- The entire dataset must fit in the memory of a single machine as it needs to be copied to the driver when collect is called.
- `collect` should not be used on large datasets. 

## Spark UI
- You can monitor the progress of a job through the Spark web UI.
- The Spark UI is available on port 4040 of the driver node.
- It’s very useful, especially for tuning and debugging.

## Running a spark application
`spark-submit` lets you send your application code to a cluster and launch it to execute there. Upon submission, the application will run until it exits (completes the task) or encounters an error. 

``` bash
./bin/spark-submit \
  --class org.apache.spark.examples.SparkPi \
  --master local \
  ./path/to/your/example.jar
```

By changing the master argument of spark-submit, we can also submit the same application to a cluster running Spark’s standalone cluster manager, Mesos or YARN.

## Datasets
- Datasets are type safe version of Spark's strcutured API.
- The Dataset API gives users the ability to assign a Java/Scala class to the records within a DataFrame and manipulate it as a collection of typed objects, similar to a Java ArrayList or Scala Seq. 
- The APIs available on Datasets are type-safe, meaning that you cannot accidentally view the objects in a Dataset as being of another class than the class you put in initially.
- The Dataset class is parameterized with the type of object contained inside: `Dataset<T>` in Java and `Dataset[T]` in Scala. 

## Spark structured APIs

These APIs refer to three core types of distributed collection APIs:
1. Datasets
2. DataFrames
3. SQL tables and views

## RDD

### How to create RDD

- Take an existing collection in your program and pass it to Spark Context's `parallelize` method. 
- All the elements in the collection will then be copied to form a distributed dataset that can be operated in parallel.
- It's very handy to create an RDD with little effort
- However, not very practical for working with large datasets.
- Practically, RDDs are created from external storage by calling `textFile` method on the spark context.
- The external storage is usually a distributed file system like HDFS.
- There are other data sources that can be integrated with Spark and used to create RDDs including JDBC, Cassandra, Elasticsearch etc.

