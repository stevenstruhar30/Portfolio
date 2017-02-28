//
//  main.c
//  Assignment3
//
//  Created by Steve Struhar on 10/22/16.
//  Copyright © 2016 Steve Struhar. All rights reserved.
//

#include <string.h>
#include "List.h"
#include <ctype.h>


int main(int argc, const char * argv[])
{
    head = NULL;//Start our list out as null
    while(1)
    {
        char input[100];
        input[0] = '\0';
        
        printf("Usage:\nEnter desired operation: Insert or Print. Exit to quit.\n");
        scanf("%6s", input);
        // Check to see if we have some valid input...
        if((input[0] == '\0') || ((strcmp(input,"Exit") != 0) && (strcmp(input,"Insert") != 0) && (strcmp(input,"Print") != 0)))
        {
            // Otherwise, we'll collect some input
            printf("Unrecognized input. Use Insert or Print. Exit to quit.\n");
            input[0] = '\0';
            scanf("%99s", input);
        }
        if(strcmp(input,"Exit") == 0)
        {
            // We'll quit if they type Exit
            return 0;
        }
        if(strcmp(input,"Insert") == 0)
        {
            // They want to add a node, so let's collect it.
            char id[100];
            id[0] = '\0';
            unsigned int inputId =  0;
            printf("Enter an id to insert.\n");
            while(id[0] == '\0')
            {
                // Positive integer collection
                
                scanf(" %s", id);
                if(isalpha(id[0]))
                {
                    printf("That was a letter. Try again.\n");
                    id[0] = '\0';
                    continue;
                }
                else if(id[0] == '-')
                {
                    printf("That was a negative number or something. Try again.\n");
                    id[0] = '\0';
                    continue;
                }
                else if(isdigit(id[0]))
                {
                    int clean = 1;
                    // Every element must be a digit
                    for(int i = 1; i < sizeof(id); i++)
                    {
                        if(id[i] != '\0')
                        {
                            if(!isdigit(id[i]))
                            {
                                clean = 0;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    if(clean == 1)
                    {
                        inputId = atoi(id);
                    }
                    else
                    {
                        printf("Your input contains a letter. Try again.\n");
                        id[0] = '\0';
                        continue;
                    }
                }
                else // It was something else like a # or $
                {
                    printf("That was weird input. Try again.\n");
                    id[0] = '\0';
                    continue;
                }
            }
            // We got here so I proclaim that x is properly sanitized
            addNode(inputId);
            printf("Inserted %u\n", inputId);
            
        }
        else if (strcmp(input,"Print") == 0)
        {
            if(head == NULL)
            {
                printf("Nothing to print.\n");
            }
            else
            {
                printNodes();
            }
        }
    }
    return 0;
}
