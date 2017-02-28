//
//  List.h
//  Assignment3
//
//  Created by Steve Struhar on 10/22/16.
//  Copyright © 2016 Steve Struhar. All rights reserved.
//

#include <stdlib.h>
#include <stdio.h>

/*
 Struct for our list
 */
typedef struct node
{
    unsigned int id;
    struct node * next;
}node;


//Head element
node *head;


/*
 Add an element to the sorted list or create a new list
 */
void addNode(unsigned int id)
{
    node *cursor, *newNode;
    
    //Create a new node
    newNode = (node *)malloc(sizeof(node));
    if (newNode == NULL)
    {
        printf("Error adding new node.");
        exit (1);//exit with an error. Out of memory.
    }
    // Set the id to the desired id
    newNode->id = id;
    
    //Create a new list if one does not exist
    if (head == NULL)
    {
        head = newNode;
        newNode->next = NULL;
        return;
    }
    
    //If our new id belongs before the head...
    //               \/ remove this '=' to prevent duplicates at the head node
    if (newNode->id <= head->id)
    {
        newNode->next = head;
        head = newNode;
        return;
    }
    
    //Otherwise figure out where it goes in the list
    cursor = head;
    while ((cursor->next != NULL))
    {
        //                \/ remove this '=' to prevent duplicates
        if ((newNode->id <= cursor->next->id))
        {
            //               \/ remove this '=' to prevent duplicates
            if((newNode->id >= cursor->id))            {
                //Insert it into the list
                newNode->next = cursor->next;
                cursor->next = newNode;
                return;
            } else
            {
                // Otherwise, we could delete it here if we simply remove all of the
                // '=' operators from the above insertions. This will prevent duplicates.
                free(newNode);
                return;
            }
        }
        cursor = cursor->next; // move to the next node.
    }
    
    //We got to the end, so it's the new tail
    if (cursor->id == newNode->id)
    {
        // We'll only get here if we do not allow duplicates
        free(newNode);
        return;
    } else
    {
        cursor->next = newNode;
        newNode->next = NULL;
        return;
    }
}


/*
 Print out all of the node data nicely formatted
 */
void printNodes()
{
    node * cursor;
    cursor = head;
    printf("\n");
    while(cursor)
    {
        printf("[ ");
        if(cursor == head)
        {
            printf("(head) ");
        }
        printf("%d ", cursor->id);
        printf("]->");
        cursor = cursor->next;
    }
    printf("[ NULL ]\n\n");
}
