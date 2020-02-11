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
- To trigger the computation, we run an action.
- An action instructs Spark to compute a result from a series of transformations.
- There are 3 kinds of actions:
    - Actions to view data in the console
    - Actions to collect data to native objects in the respective language
    - Actions to write to output data sources

## Spark UI
- You can monitor the progress of a job through the Spark web UI.
- The Spark UI is available on port 4040 of the driver node.
- It’s very useful, especially for tuning and debugging.