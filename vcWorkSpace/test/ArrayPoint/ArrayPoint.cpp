// ArrayPoint.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
//注意指针数组和数组指针分别是如何指向二维数组的
#include <stdio.h>

main()
{
    static int m[3][4]={0,1,2,3,4,5,6,7,8,9,10,11};/* 定义二维数组m并初始化*/ 
    int (*p)[4];//数组指针  p是指针，指向一维数组,每个一维数组有4个int元素
    int i,j;
    int *q[3];//指针数组 q是数组，数组元素是指针，3个int指针
    p=m;    //p是指针，可以直接指向二维数组
    printf("--数组指针输出元素--\n");
    for(i=0;i<3;i++)/*输出二维数组中各个元素的数值*/
    {
        for(j=0;j<4;j++) 
        {
            printf("%3d ",*(*(p+i)+j));
        }
        printf("\n");
    }
    printf("\n");
    for(i=0;i<3;i++,p++)//p可看成是行指针
    {
        printf("%3d ",**p);//每一行的第一个元素
        printf("%3d ",*(*p+1));//每一行的第二个元素
        printf("%3d ",*(*p+2));//每一行的第三个元素
        printf("%3d ",*(*p+3));//每一行的第四个元素
        printf("\n");
    }
    printf("\n");
    printf("--指针数组输出元素--\n");
    for(i=0;i<3;i++)
        q[i]=m[i];//q是数组，元素q[i]是指针
    for(i=0;i<3;i++)
    {
        for(j=0;j<4;j++)
        {
            printf("%3d ",q[i][j]);//q[i][j]可换成*(q[i]+j)
        }
        printf("\n");
    }
    printf("\n");
    q[0]=m[0];
    for(i=0;i<3;i++)
    {
        for(j=0;j<4;j++)
        {
            printf("%3d ",*(q[0]+j+4*i));
        }
        printf("\n");
    }
    printf("\n");
    
}
