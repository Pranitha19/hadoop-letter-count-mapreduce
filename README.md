# Hadoop Letter Count MapReduce Project

Developed a Java-based Hadoop MapReduce application that processed 10 Project Gutenberg datasets using HDFS and YARN on a Docker-based Hadoop 3.3.6 cluster. Implemented case-insensitive letter frequency analysis and total letter aggregation.

This is an academic project.

## Overview

This project implements a Hadoop MapReduce application in Java to calculate the frequency of each letter (A-Z) from a collection of Project Gutenberg text files. The application performs case-insensitive letter counting and also calculates the total number of letters across all input files.

The project was developed as part of a Big Data course using Hadoop 3.3.6 running inside Docker containers on macOS.

---

# Project Objectives

The goals of this project were:

* Learn the Hadoop ecosystem.
* Understand the MapReduce programming model.
* Work with HDFS (Hadoop Distributed File System).
* Execute distributed processing using YARN.
* Develop a Java-based MapReduce application.
* Process multiple large text files in parallel.
* Generate analytical output from distributed datasets.

---

# Technologies Used

| Technology     | Purpose                                |
| -------------- | -------------------------------------- |
| Java           | MapReduce application development      |
| Hadoop 3.3.6   | Distributed data processing            |
| HDFS           | Distributed storage                    |
| YARN           | Resource management and job scheduling |
| Docker Desktop | Containerized Hadoop cluster           |
| VS Code        | Source code editing                    |
| macOS (M1)     | Development environment                |

---

# Hadoop Architecture

The Hadoop cluster was deployed using Docker containers.

The cluster consisted of four major Hadoop services:

### NameNode

Responsible for:

* Managing HDFS metadata
* Tracking file locations
* Coordinating HDFS operations

### DataNode

Responsible for:

* Storing actual file blocks
* Serving read/write requests

### ResourceManager

Responsible for:

* Managing cluster resources
* Scheduling MapReduce jobs
* Monitoring application execution

### NodeManager

Responsible for:

* Executing Map tasks
* Executing Reduce tasks
* Monitoring container execution

---

# Dataset

The input dataset consisted of 10 Project Gutenberg text files.

Files used:

* pg11.txt
* pg16.txt
* pg45.txt
* pg74.txt
* pg815.txt
* pg829.txt
* pg1259.txt
* pg1260.txt
* pg2701.txt
* pg98.txt

These files were uploaded into HDFS before executing the MapReduce job.

---

# Problem Statement

Given a collection of text files:

1. Count the frequency of every alphabetic character.
2. Ignore case differences.
3. Ignore spaces, punctuation, and numbers.
4. Generate one additional output record named:

total

containing the total number of letters processed.

Example Input:

The quick brown fox

Mapper Output:

(T,1)
(H,1)
(E,1)
(Q,1)
(U,1)
(I,1)
(C,1)
(K,1)

Reducer Output:

T 1
H 1
E 1
Q 1
U 1
I 1
C 1
K 1

total 16

---

# MapReduce Workflow

The application follows the standard Hadoop MapReduce processing pipeline.

Input Files
↓
Mapper
↓
Shuffle & Sort
↓
Reducer
↓
Output

---

# Mapper Design

The Mapper performs the following operations:

### Step 1

Read input text line-by-line.

Example:

The quick brown fox

### Step 2

Convert the line to uppercase.

THE QUICK BROWN FOX

This ensures case-insensitive processing.

### Step 3

Process each character individually.

### Step 4

Check whether the character is alphabetic.

Example:

A-Z

### Step 5

Emit:

(letter, 1)

Example:

(T,1)
(H,1)
(E,1)

The Mapper ignores:

* Spaces
* Numbers
* Symbols
* Punctuation

---

# Shuffle and Sort Phase

The Shuffle and Sort phase is performed automatically by Hadoop.

Mapper Output:

(A,1)
(A,1)
(A,1)
(B,1)

After Shuffle and Sort:

A → [1,1,1]

B → [1]

This groups identical keys together before they reach the Reducer.

---

# Reducer Design

The Reducer receives grouped values.

Example:

A → [1,1,1,1]

The Reducer computes:

A → 4

This process is repeated for every letter.

The Reducer also maintains a running total of all letters encountered.

After processing all keys, the Reducer emits:

total → overall_count

This satisfies the assignment requirement.

---

# Building the Project

## Compile

Because Hadoop libraries are required, the Java source file is compiled with the Hadoop classpath.

Example:

javac -cp "<hadoop libraries>" LetterCount.java

Generated classes:

* LetterCount.class
* LetterCount$LetterMapper.class
* LetterCount$LetterReducer.class

---

## Package

The compiled classes are packaged into a Hadoop executable JAR.

Example:

jar -cvf LetterCount.jar *.class

Generated artifact:

LetterCount.jar

---

# HDFS Setup

Create input directory:

hdfs dfs -mkdir -p /user/ucmo/gutenberg-in

Upload dataset:

hdfs dfs -put *.txt /user/ucmo/gutenberg-in/

Verify:

hdfs dfs -ls /user/ucmo/gutenberg-in

---

# Executing the MapReduce Job

Run:

yarn jar LetterCount.jar LetterCount /user/ucmo/gutenberg-in /user/ucmo/gutenberg-out

Hadoop automatically:

1. Splits the input.
2. Launches Mapper tasks.
3. Performs Shuffle and Sort.
4. Executes Reducer tasks.
5. Stores results in HDFS.

---

# Execution Statistics

Successful execution produced:

* Total Input Files: 10
* Map Input Records: 155,797
* Map Output Records: 5,916,046
* Reduce Output Records: 49

Job Status:

Completed Successfully

---

# Output Verification

Verify output:

hdfs dfs -ls /user/ucmo/gutenberg-out

Output:

_SUCCESS
part-r-00000

Display results:

hdfs dfs -cat /user/ucmo/gutenberg-out/part-r-00000

The output contains:

* Letter frequencies
* Total letter count

---

# Challenges Encountered

During development:

### Hadoop Dependencies

Compilation initially failed because Hadoop libraries were unavailable locally.

Solution:

Copied Hadoop libraries from the Docker container and compiled using the Hadoop classpath.

### ResourceManager Failure

A Hadoop ResourceManager container stopped unexpectedly during execution.

Solution:

Restarted the Hadoop cluster using:

docker compose down
docker compose up -d

After restarting, the MapReduce job completed successfully.

---

# Key Concepts Learned

This project helped strengthen understanding of:

* Hadoop Ecosystem
* HDFS
* MapReduce
* Mapper Design
* Reducer Design
* Shuffle and Sort
* YARN
* Docker-Based Hadoop Clusters
* Distributed Data Processing
* Java Programming
* Big Data Fundamentals

---

# Future Enhancements

Potential improvements include:

* Word Frequency Analysis
* N-Gram Analysis
* Spark-Based Implementation
* Hive Integration
* Kafka-Based Streaming Input
* Data Visualization Dashboard

---

# Author

Pranitha Avula

Master's Student

Focus Areas:

* Big Data
* Data Engineering
* Machine Learning
* Artificial Intelligence
