package edu.ucr.cs.cs167.skudt001;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Filter {
    public static void main(String[] args) {
//        final String inputPath = args[0];
//        final String desiredCode = "200";
        final String inputPath = args[0];
        final String outputPath = args[1];
        final String desiredCode = args[2];
        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("CS167-Lab5");
        try (JavaSparkContext spark = new JavaSparkContext(conf)) {
            JavaRDD<String> logFile = spark.textFile(inputPath);
            JavaRDD<String> matchingLines = logFile.filter(line -> line.split("\t")[5].equals(desiredCode));
            System.out.printf("The file '%s' contains %d lines with response code %s\n", inputPath, matchingLines.count(), desiredCode);
            matchingLines.saveAsTextFile(outputPath);
            //System.out.printf("Number of lines in the log file %d\n", logFile.count());
        }
    }
}