//
//  AddItemViewController.m
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import <Parse/Parse.h>
#import "AddItemViewController.h"

@interface AddItemViewController ()

@end

@implementation AddItemViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    if (self.objectId != nil)
    {
        PFUser* user = [PFUser currentUser];
        NSString* objectName = [NSString stringWithFormat:@"%@%@", [user username], @"_data"];
    
        PFQuery* query = [PFQuery queryWithClassName:objectName];
        [query getObjectInBackgroundWithId:self.objectId block:^(PFObject* object, NSError *error)
        {
            itemName.text = [object objectForKey:@"name"];
            itemRarity.text = [object objectForKey:@"rarity"];
            itemType.text = [object objectForKey:@"type"];
            itemGold.text = [object objectForKey:@"gold"];
            itemSilver.text = [object objectForKey:@"silver"];
            itemCopper.text = [object objectForKey:@"copper"];
        }];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)onClick:(id)sender
{
    if (self.objectId != nil)
    {
        PFUser* user = [PFUser currentUser];
        NSString* objectName = [NSString stringWithFormat:@"%@%@", [user username], @"_data"];
        
        PFQuery* query = [PFQuery queryWithClassName:objectName];
        [query getObjectInBackgroundWithId:self.objectId block:^(PFObject* objectToSave, NSError *error)
         {
             
             objectToSave[@"name"] = itemName.text;
             objectToSave[@"rarity"] =  itemRarity.text;
             objectToSave[@"type"] =  itemType.text;
             objectToSave[@"gold"] =  itemGold.text;
             objectToSave[@"silver"] =  itemSilver.text;
             objectToSave[@"copper"] =  itemCopper.text;
             
             [objectToSave saveEventually];
         }];
        
        [self.navigationController popViewControllerAnimated:true];
    }
    else
    {
        PFUser* user = [PFUser currentUser];
        NSString* objectName = [NSString stringWithFormat:@"%@%@", [user username], @"_data"];
    
        PFObject* objectToSave = [PFObject objectWithClassName:objectName];
    
        objectToSave[@"name"] = itemName.text;
        objectToSave[@"rarity"] =  itemRarity.text;
        objectToSave[@"type"] =  itemType.text;
        objectToSave[@"gold"] =  itemGold.text;
        objectToSave[@"silver"] =  itemSilver.text;
        objectToSave[@"copper"] =  itemCopper.text;
    
        [objectToSave saveEventually];
    
        [self.navigationController popViewControllerAnimated:true];
    }
}

@end
