package com.arpan.spark.util

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext

object SparkUtils {

    fun getSparkContext(appName: String, masterUrl: String = "local[*]"): JavaSparkContext {
        val sparkConf = SparkConf().setAppName(appName).setMaster(masterUrl)
        return JavaSparkContext(sparkConf)
    }
}