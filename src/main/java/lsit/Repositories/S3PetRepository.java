package lsit.Repositories;

import java.net.URI;
import java.util.*;

import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import lsit.Models.Pet;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Primary
@Repository
public class S3PetRepository implements IPetRepository {

    final String BUCKET="pizzeria_bucket";
    final String PREFIX="petstore/pets/";
    final String ENDPOINT_URL="https://storage.googleapis.com";

    S3Client s3client;
    AwsCredentials awsCredentials;
    
    public S3PetRepository(){
        Dotenv dotenv = Dotenv.load();

        String accessKey = dotenv.get("ACCESS_KEY");
        String secretKey = dotenv.get("SECRET_KEY");

        if (accessKey == null || secretKey == null) {
            throw new IllegalStateException("AWS credentials are not set in environment variables");
        }
        awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        s3client = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .endpointOverride(URI.create(ENDPOINT_URL))
            .region(Region.of("auto"))
            .build();
    }

    public void add(Pet p){
        try{
            p.id = UUID.randomUUID();

            ObjectMapper om = new ObjectMapper();

            String petJson = om.writeValueAsString(p);
            
            s3client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + p.id.toString())
                .build(),
                RequestBody.fromString(petJson)
            );
        }
        catch(JsonProcessingException e){}
    }

    public Pet get(UUID id){
        try{
            var objectBytes = s3client.getObject(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + id.toString())
                .build()
            ).readAllBytes();

            ObjectMapper om = new ObjectMapper();
            Pet p = om.readValue(objectBytes, Pet.class);

            return p;
        }catch(Exception e){
            return null;
        }
    }

    public void remove(UUID id){
        s3client.deleteObject(DeleteObjectRequest.builder()
            .bucket(BUCKET)
            .key(PREFIX + id.toString())
            .build()
        );  
    }

    public void update(Pet p){
        try{
            Pet x = this.get(p.id);
            if(x == null) return;

            ObjectMapper om = new ObjectMapper();
            String petJson = om.writeValueAsString(p);
            s3client.putObject(PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(PREFIX + p.id.toString())
                .build(),
                RequestBody.fromString(petJson)
            );
        }
        catch(JsonProcessingException e){}
    }

    public List<Pet> list(){
        List<Pet> pets = new ArrayList<>();
        List<S3Object> objects = s3client.listObjects(ListObjectsRequest.builder()
          .bucket(BUCKET)
          .prefix(PREFIX)
          .build()  
        ).contents();
    
        for(S3Object o : objects){
            try {
                // Only process if the key is longer than the prefix
                String key = o.key();
                if (key.length() > PREFIX.length()) {
                    String idString = key.substring(PREFIX.length());
                    UUID id = UUID.fromString(idString);
                    Pet p = this.get(id);
                    if (p != null) {
                        pets.add(p);
                    }
                }
            } catch (Exception e) {
                // Log the error and continue processing other objects
                System.err.println("Error processing object: " + o.key());
                e.printStackTrace();
            }
        }
    
        return pets;
    }
}
