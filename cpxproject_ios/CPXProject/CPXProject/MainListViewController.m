//
//  MainListViewController.m
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import <Parse/Parse.h>

#import "MainListViewController.h"
#import "AddItemViewController.h"

@interface MainListViewController ()

@end

@implementation MainListViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"Item List";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    UIBarButtonItem* logOutItem = [[UIBarButtonItem alloc] initWithTitle:@"Log Out" style:UIBarButtonItemStylePlain target:self action:@selector(logOutPressed)];
    [self.navigationItem setLeftBarButtonItem:logOutItem];
    
    PFUser* user = [PFUser currentUser];
    NSString* objectName = [NSString stringWithFormat:@"%@%@", [user username], @"_data"];
    
    PFQuery* query = [PFQuery queryWithClassName:objectName];
    
    query.cachePolicy = kPFCachePolicyNetworkElseCache;
    
    [query findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        if (!error)
        {
            data = objects;
            [mainTable reloadData];
        }
        else
        {
            //error
        }
    }];
    
}

-(void) logOutPressed
{
    [PFUser logOut];
    //pop to first screen
    UINavigationController * navigationController = self.navigationController;
    [navigationController popToRootViewControllerAnimated:NO];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)onClick:(id)sender
{
    AddItemViewController* vc = [[AddItemViewController alloc] initWithNibName:@"AddItemViewController" bundle:nil];
    [self.navigationController pushViewController:vc animated:true];
}

//table code
-(NSInteger)tableView:(UITableView*)tableView numberOfRowsInSection:(NSInteger)section
{
    return [data count];
}

-(UITableViewCell*)tableView:(UITableView*)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString* cellID = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellID];
    
    if (!cell)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellID];
    }
    
    PFObject* currObject = [data objectAtIndex:[indexPath row]];
    NSString* displayString = [NSString stringWithFormat:@"%@ [%@ %@] - %@g, %@s, %@c", [currObject objectForKey:@"name"]
                               ,[currObject objectForKey:@"rarity"], [currObject objectForKey:@"type"], [currObject objectForKey:@"gold"], [currObject objectForKey:@"silver"], [currObject objectForKey:@"copper"]];
    
    cell.textLabel.text = displayString;
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{

}


//editing table cells

-(UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewCellEditingStyleDelete;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return YES;
}

-(void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if(editingStyle == UITableViewCellEditingStyleDelete)
    {
        PFObject* objectToDelete = [data objectAtIndex:[indexPath row]];
        [objectToDelete deleteEventually];
        
        [data removeObjectAtIndex:[indexPath row]];
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:true];
    }
}

-(void)setEditing:(BOOL)editing animated:(BOOL)animated
{
    [super setEditing:editing animated:animated];
    
    if(editing)
    {
        
        [mainTable setEditing:true animated:true];
    }
    else
    {
        [mainTable setEditing:false animated:true];
    }
}



@end
