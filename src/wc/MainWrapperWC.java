package wc;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
//import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;






public class MainWrapperWC extends Configured implements Tool {

	// logger declaration and initialization
	static final Logger logger = Logger.getLogger(MainWrapperWC.class);

	@SuppressWarnings("deprecation")
	@Override
	public int run(String args[]) throws IOException {
		// user should provide at-least one input and output respectively
		if (args.length < 2) {
			System.out.println("Please give valid inputs");
			logger.error("Error due to invalid inputs ");
			return -1;
		}

		// capturing the start time of task 1
		long start = System.currentTimeMillis();
		// job creation
		JobConf conf = new JobConf(MainWrapperWC.class);
		// job name
		conf.setJobName("task_1");
		// selecting the partition class
		conf.setPartitionerClass(PartitionerWordCount.class);
		// no. of output files or partition to be done - here it is given as 2 for task1

		conf.setNumReduceTasks(2);
		
		 int outputFile=args.length - 1;
		 System.out.println("args.length OutFile " + outputFile);
		// reads the input files here
		
		  for (int a = 0; a < outputFile-1; a++) { 
			  System.out.println("A  >>> " + a);
			  MultipleInputs.addInputPath(conf, new
		  Path(args[a]), TextInputFormat.class, Mapper1.class);
		  
		  }
		 
			/*
			 * MultipleInputs.addInputPath(conf, new Path(args[0]), TextInputFormat.class,
			 * Mapper_Task1.class); MultipleInputs.addInputPath(conf, new Path(args[1]),
			 * TextInputFormat.class, Mapper_Task1.class); MultipleInputs.addInputPath(conf,
			 * new Path(args[2]), TextInputFormat.class, Mapper_Task1.class);
			 */
		  
		  //outputFile
		
		// set the output file where the file output needs to be written here
		  System.out.println("Out file path " + outputFile );
		  FileOutputFormat.setOutputPath(conf, new Path(args[outputFile-1]));
		  //FileOutputFormat.setOutputPath(conf, new Path(args[3]));
       
		
		// Delete the output file if already exists
//		FileSystem fs = FileSystem.get(getConf());
//		if (fs.exists(new Path(args[3]))) {
//			fs.delete(new Path(args[3]), true);
//		}
		
		FileSystem fs = FileSystem.get(getConf());
		if (fs.exists(new Path(args[outputFile]))) {
			fs.delete(new Path(args[outputFile]), true);
		}
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
		System.out.println(" Task 1 " + (end - start) + "ms");
		logger.info("Task 1 takes following milliseconds to complete  " + (end - start) + "ms ");
		return 0;
	}

	// Main Method
	public static void main(String args[]) throws Exception {

		// Main method which gets invoked when this jar is executed
		// using the log properties from
		logger.info("Task 1 starts ");
		PropertyConfigurator.configure("/user/s3822167/log4J.properties");
		// executing the run method using tool runner
		int exitCode = ToolRunner.run(new MainWrapperWC(), args);
		System.out.println(exitCode);
		logger.info("Task 1 ends with exit code  " + exitCode);

	}

}
