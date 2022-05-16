/* Decompiler 3ms, total 1365ms, lines 25 */
package com.hdfc.api;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.HeadBucketRequest;

public class Test1 {
   public static void main(String[] args) {
      System.out.println(bucketIfExist("smartcn-test-files"));
   }

   static boolean bucketIfExist(String bucketName) {
      AmazonS3 s3Client = (AmazonS3)((AmazonS3ClientBuilder)AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)).build();

      try {
         HeadBucketRequest request = new HeadBucketRequest(bucketName);
         s3Client.headBucket(request);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }
}
