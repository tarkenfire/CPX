//
//  MainListViewController.h
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MainListViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>
{
    NSMutableArray* data;
    
    IBOutlet UITableView* mainTable;
}

-(IBAction)onClick:(id)sender;

@end
