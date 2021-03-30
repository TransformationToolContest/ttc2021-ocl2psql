# The TTC 2021 OCL2PSQL Case

This is a case for the  TTC 2021 on the translation of OCL queries to SQL, for a certain database schema (OCL2SQL).
The case study is based on this reference implementation:

* Hoang Nguyen Phuoc Bao and Manuel Clavel, OCL2PSQL: An OCL-to-SQL Code-Generator for Model-Driven Engineering, FDSE 2019. ([doi:10.1007/978-3-030-35653-8_13](http://doi.org/10.1007/978-3-030-35653-8_13))

## Case materials

The repository is structured as follows:

* `docker`:
  * `Dockerfile` build the MySQL image for running the benchmark.
  * `init.sql` initialize the database for running the benchmark.
* `docs`:
  * `challenges.txt` lists the various challenges (OCL queries) to be met by your transformation, organized by stage and challenge.
  * `scenarios.txt` runs some SQL queries over the various scenarios, showing what the expected results would be for them.
* `metamodels` includes several metamodels:
  * `ocl.ecore` has three EMF packages: one for data models (the database schemas), a simplified version of OCL expressions, and a simplified version of predefined OCL types.
  * `sql.ecore` has a metamodel for describing SQL SELECT queries.
* `models` includes three classes of models:
  * `CarPerson.xmi` describes the database schema for the sample Car-Person database.
  * `StageXChallengeY.xmi` has the XMI representation of the OCL query in the challenge Y of stage X.

## Solution prerequisites

### Reference solution

The reference solution in `solutions/ReferenceXMI` uses four JAR files that provide the OCL metamodel, 
SQL metamodel, OCL java representation, and the OCL2PSQL mapping. These JAR files are located in `lib` directory.
You must install these dependencies into your local Maven repository before you build the reference solution.
Assuming that Maven is in your `PATH`, you can run this Bash script:

```
cd solutions/ReferenceXMI
./install-jars.sh
```

Furthermore, the reference solution uses the MySQL database system built from the MySQL docker image in `docker` directory.
In order to test the correctness of the reference solution, you must first build the MySQL container by using the following commands:

```
cd docker/
docker build --tag my-ttc2021 .
docker run -p 8083:3306 my-ttc2021
```

in which `8083` is the localhost port number that will be used to connect with the MySQL container. Note that, in case the port is busy, feel free to change it to another one, but remember to change it in the `config.json` as well.

## Using the framework

The `scripts` directory contains the `run.py` script.
At a first glance, invoke it without any arguments so that the solution will be built, benchmarked, running times visualized and the results compared to the reference solution's.
One might fine tune the script for the following purposes:
* `run.py -b` -- builds the projects
* `run.py -b -s` -- builds the projects without testing
* `run.py -m` -- run the benchmark without building
* `run.py -v` -- visualizes the results of the latest benchmark
* `run.py -c` -- check results by comparing them to the reference output. The benchmark shall already been executed using `-m`.
* `run.py -t` -- build the project and run tests (usually unit tests as defined for the given solution)
* `run.py -d` -- runs the process in debug mode, i.e. with the `Debug` environment variable set to `true`

The `config` directory contains the configuration for the scripts:
* `config.json` -- configuration for the model generation and the benchmark
  * *Note:* the timeout as set in the benchmark configuration (default: 6000 seconds) applies to the gross cumulative runtime of the tool for a given changeset and update sequences. This also includes e.g. Initialization time which is not required by the benchmark framework to be measured.
    Timeout is only applied to the solutions' run stage (see `-m` for `run.py`), so it is not applied to e.g. the build stage (see `-b` for `run.py`).
* `reporting.json` -- configuration for the visualization

### Running the benchmark

The script runs the benchmark for the given number of runs, for the specified tools and change sequences.

The benchmark results are stored in a CSV file. The header for the CSV file is stored in the `output/header.csv` file.

## Reporting and visualization

Make sure you read the `README.md` file in the `reporting` directory and install all the requirements for R.

## Implementing the benchmark for a new tool

To implement a tool, you need to create a new directory in the solutions directory and give it a suitable name.
