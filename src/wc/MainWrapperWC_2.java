package wc;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import org.apache.hadoop.fs.FileSystem;

public class MainWrapperWC_2 extends Configured implements Tool {

	// logger declaration and initialization
	static final Logger logger = Logger.getLogger(MainWrapperWC_2.class);

	@SuppressWarnings("deprecation")
	@Override
	public int run(String args[]) throws IOException {
		// user should provide at-least one input and output respectively
		if (args.length < 2) {
			System.out.println("Please give valid inputs");
			logger.error("Error due to invalid inputs ");
			return -1;
		}

		// capturing the start time of task 2
		long start = System.currentTimeMillis();
		// job creation
		JobConf conf = new JobConf(MainWrapperWC_2.class);
		// job name
		conf.setJobName("task_2");
		conf.setNumReduceTasks(3);
		System.out.println("Args length " + args.length);
		 int outputFile=args.length - 1;
		 System.out.println("args.length OutFile " + outputFile);
		// reads the input files here
		
		  for (int a = 0; a < outputFile; a++) { 
			  System.out.println("A  >>> " + a + " " + args[a] );
			  MultipleInputs.addInputPath(conf, new
		     Path(args[a]), TextInputFormat.class, Mapper_Task2.class);
		  
		  }
		  //outputFile
		
		// set the output file where the file output needs to be written here
		  FileOutputFormat.setOutputPath(conf, new Path(args[outputFile]));
		

		// Delete the output file if already exists
		FileSystem fs = FileSystem.get(getConf());
		if (fs.exists(new Path(args[outputFile]))) {
			fs.delete(new Path(args[outputFile]), true);
		}

		// Combining without preserving the state
		conf.setCombinerClass(Combiner.class);
		// reducer class used
		conf.setReducerClass(ReducerWC.class);
		// setting the formats of output here
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(IntWritable.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);

		// starting the job here
		JobClient.runJob(conf);

		// the moment task ends and logging the end and total time
		long end = System.currentTimeMillis();
		System.out.println(" Task 2 " + (end - start) + "ms");
		logger.info("Task 2 takes following milliseconds to complete  " + (end - start) + "ms ");
		return 0;
	}

	// Main Method
	public static void main(String args[]) throws Exception {

		// Main method which gets invoked when this jar is executed
		// using the log properties from
		logger.info("Task 2 starts ");
		//PropertyConfigurator.configure("/user/s3822167/log4J.properties");
		 Properties properties=new Properties();
		    properties.setProperty("log4j.rootLogger","INFO,console,FILE");

		    properties.setProperty("log4j.appender.FILE",     "org.apache.log4j.FileAppender");
		    properties.setProperty("log4j.appender.FILE.File",  "logger2.log");
		    properties.setProperty("log4j.appender.FILE.ImmediateFlush","false");

		    properties.setProperty("log4j.appender.FILE.Threshold", "debug");
		    properties.setProperty("log4j.appender.FILE.Append", "true");
		    properties.setProperty("log4j.appender.FILE.layout",  "org.apache.log4j.PatternLayout");
		    properties.setProperty("log4j.appender.FILE.layout.conversionPattern","%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

		    properties.setProperty("log4j.appender.console", "org.apache.log4j.ConsoleAppender");
		    properties.setProperty("log4j.appender.console.Target", "System.out");
		    properties.setProperty("log4j.appender.console.layout", "org.apache.log4j.PatternLayout");
		    properties.setProperty("log4j.appender.consoleAppender.layout.ConversionPattern", "[%t] %-5p %c %x - %m%n");
		    PropertyConfigurator.configure(properties);
		
		
		int exitCode = ToolRunner.run(new MainWrapperWC_2(), args);
		System.out.println(exitCode);
		logger.info("Task 2 ends with exit code  " + exitCode);
	}
}
