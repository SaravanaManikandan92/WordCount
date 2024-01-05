package wc;

import java.io.IOException;

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
//import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.fs.FileSystem;

public class MainWrapperWC_3 extends Configured implements Tool {
	@SuppressWarnings("deprecation")
	@Override
	public int run(String args[]) throws IOException {
		if (args.length < 2) {
			System.out.println("Please give valid inputs");
			return -1;
		}

		/*
		 * Configuration conf = getConf(); JobConf job = new JobConf(conf);
		 * job.setJobName("ChainJob"); job.setInputFormat(TextInputFormat.class);
		 * job.setOutputFormat(TextOutputFormat.class);
		 * FileInputFormat.setInputPaths(job, in); FileOutputFormat.setOutputPath(job,
		 * out);
		 */
		
		
		// starting time
        long start = System.currentTimeMillis();
		JobConf conf = new JobConf(MainWrapperWC_3.class);
		conf.setJobName("wordcount");
		// PartitionerWordCount.class
	    //conf.setPartitionerClass(PartitionerWordCount.class);
		//conf.setNumReduceTasks(2);
		// TAB separated input File 1 3littlepigs
		for(int a=0;a<args.length-1;a++)
		{
			MultipleInputs.addInputPath(conf, new Path(args[a]), TextInputFormat.class, MapperWC_1_words.class);
			
			
		}
		
		// ";" separated input file 2 melbourne
		//MultipleInputs.addInputPath(conf, new Path(args[1]), TextInputFormat.class, Mapper_task_1.class);
		// ";" separated input file 3 RMIT
		//MultipleInputs.addInputPath(conf, new Path(args[2]), TextInputFormat.class, Mapper_task_1.class);
		FileOutputFormat.setOutputPath(conf, new Path(args[args.length-1]));
		//Delete the output file if already exists 
		FileSystem fs = FileSystem.newInstance(getConf());
		if(fs.exists(new Path(args[args.length-1])))
		{
			fs.delete(new Path(args[args.length-1]),true);
		}
       //Combiner with preserving the state
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(IntWritable.class);
		conf.setReducerClass(ReducerWC.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		JobClient.runJob(conf);
		
		 long end = System.currentTimeMillis();
	        System.out.println(" Task 3 " +
	                                    (end - start) + "ms");
		return 0;
	}

	// Main Method
	public static void main(String args[]) throws Exception {

		// check whether the output directory exists

		int exitCode = ToolRunner.run(new MainWrapperWC_3(), args);
		System.out.println(exitCode);
	}
}
