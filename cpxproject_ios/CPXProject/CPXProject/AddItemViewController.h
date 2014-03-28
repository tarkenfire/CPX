//
//  AddItemViewController.h
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AddItemViewController : UIViewController
{
    IBOutlet UITextField* itemName;
    IBOutlet UITextField* itemRarity;
    IBOutlet UITextField* itemType;
    IBOutlet UITextField* itemGold;
    IBOutlet UITextField* itemSilver;
    IBOutlet UITextField* itemCopper;
}

@property (nonatomic) NSString* objectId;

-(IBAction)onClick:(id)sender;

@end
