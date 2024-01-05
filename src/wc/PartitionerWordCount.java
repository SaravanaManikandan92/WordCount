package wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

public class PartitionerWordCount implements Partitioner<Text, IntWritable> {

    @Override
    public int getPartition(Text key, IntWritable value, int numReduceTasks) {
    	if(key.toString().equalsIgnoreCase("shortWords") || key.toString().equalsIgnoreCase("mediumWords"))
    	{
    		return 0;
    	}
    	if(key.toString().equalsIgnoreCase("longWords")|| key.toString().equalsIgnoreCase("extraLongWords"))
    	{
    		return 1 % numReduceTasks;}
    	return 0;
    }


	@Override
	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub
		
	}    
}
