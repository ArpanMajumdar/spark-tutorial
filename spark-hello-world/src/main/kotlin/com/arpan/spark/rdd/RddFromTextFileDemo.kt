package com.arpan.spark.rdd

import com.arpan.spark.util.FileUtils
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import org.slf4j.LoggerFactory

data class Airport(
    val airportId: Int,
    val airportName1: String,
    val airportName2: String,
    val city: String,
    val iataCode: String,
    val icaoCode: String,
    val latitude: String,
    val longitude: String,
    val altitude: String
)

val COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)".toRegex()

fun main() {

    val logger = LoggerFactory.getLogger("WordCount")

    // Create spark context
    val sparkConf = SparkConf().setAppName("rdd-from-text-file").setMaster("local[*]")
    val sparkContext = JavaSparkContext(sparkConf)

    // Read text file
    val airports: JavaRDD<String> = sparkContext.textFile("input/airports.text")

    // Data filter and transformation
    val airportsInUsa: JavaRDD<Airport> = airports
        .map { line ->
            val tokens = line.split(COMMA_DELIMITER)
            val strippedTokens = tokens.map { token -> token.trim() }

            logger.info(line)

            Airport(
                airportId = strippedTokens[0].toInt(),
                airportName1 = strippedTokens[1],
                airportName2 = strippedTokens[2],
                city = strippedTokens[3],
                iataCode = strippedTokens[4],
                icaoCode = strippedTokens[5],
                latitude = strippedTokens[6],
                longitude = strippedTokens[7],
                altitude = strippedTokens[8]
            )
        }
        .filter { airport -> airport.city == "\"United States\"" }

    val airportNamesInUsa: JavaRDD<String> =
        airportsInUsa.map { airport -> "${airport.airportName1} , ${airport.airportName2}" }

    // Write result to output file
    FileUtils.recursivelyDeleteDirectory("output/airportNamesInUsa")
    airportNamesInUsa.saveAsTextFile("output/airportNamesInUsa")
}

