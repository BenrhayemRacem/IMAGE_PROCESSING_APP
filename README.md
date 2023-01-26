# IMAGE_PROCESSING_APP
A desktop application written in java for processing grayscale images.

# Features :
- Read / Write PPM and PGM file format
- Calculating image metrics such as : mean and standard deviation
- Drawing the grey level histogram , the cumulative histogram
- Histogram equalization
- Linear transformation
- Adding salt and paper noise
- Applying Filters : median filter ,mean filter , outline filter
- PPM images segmentation using thresholds
- Using Otsu segmentation algorithm with opencv

# Objectives learnt :
- Learning the fundamental concepts of image processing
- Developping some of the PPM images functionalities ( read , write , calculating metrics , applying filters , performing linear transformation )
- Using javafx for the GUI
- Integrating opencv library to calculate otsu threshold , to erode and dilate images

# Demo :

## 1- Applying median filter to a noisy image (salt and paper noise )

### `Noisy image`
![image](https://user-images.githubusercontent.com/59982299/214866643-00e5c8f1-2bcb-4e0f-9dbb-d867661532ed.png) 

### `Filtered image`
![image](https://user-images.githubusercontent.com/59982299/214866909-bd3a75fe-800d-4f8f-aa64-56d79646459b.png)

## 2- Applying high-pass filter (and outline filter)

### `Original image`
![image](https://user-images.githubusercontent.com/59982299/214867734-3d69d062-518f-4ead-87b1-70362137c09f.png)

### `Filtered image`
![image](https://user-images.githubusercontent.com/59982299/214867985-236f7b76-2821-4576-9dc4-7a28d93cbdb5.png)

## 3- Image after Histogram equalization

### `Original image`
![image](https://user-images.githubusercontent.com/59982299/214867734-3d69d062-518f-4ead-87b1-70362137c09f.png)

### `Adjusted image`
![image](https://user-images.githubusercontent.com/59982299/214869324-52c4c736-014a-4eaf-b4b6-3cb827469843.png)

## 4- Linear Transformation :

![image](https://user-images.githubusercontent.com/59982299/214869889-4e6b7729-8602-4011-91b0-53314c776195.png)

## 5- Segmentation using Otsu :

### `Original image`
![image](https://user-images.githubusercontent.com/59982299/214870692-bf219a58-849c-446b-a0ea-5b2e77b063b2.png)

### `After segmentation`
![image](https://user-images.githubusercontent.com/59982299/214870881-923b5a94-6973-4c80-9bee-234699fc6db9.png)


## 6-Erosion (Kernel size is 5*5 , kernel types : rectangle , cross , ellipse ) :
![image](https://user-images.githubusercontent.com/59982299/214871663-cbf4a70a-f30b-474c-9771-9bca584c647b.png)

## 7-Dilatation (Kernel size is 5*5 , kernel types : rectangle , cross , ellipse ) :
![image](https://user-images.githubusercontent.com/59982299/214871878-7f4d1b37-125d-48fb-93ff-738c481daf8c.png)











