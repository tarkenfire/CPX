//
//  MainMenuViewController.m
//  CPXProject
//
//  Created by Michael Mancuso on 3/27/14.
//  Copyright (c) 2014 Hinode Softworks. All rights reserved.
//

#import <Parse/Parse.h>

#import "MainMenuViewController.h"
#import "LogInViewController.h"
#import "SignUpViewController.h"
#import "MainListViewController.h"

@interface MainMenuViewController ()

@end

@implementation MainMenuViewController

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
    self.title = @"CPX Project";
    
    if ([PFUser currentUser] != nil)
    {
        MainListViewController* vc = [[MainListViewController alloc] initWithNibName:@"MainListViewController" bundle:nil];
        [self.navigationController pushViewController:vc animated:true];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)onClick:(id)sender
{
    switch ([sender tag])
    {
        case 0: //sign up
        {
            SignUpViewController* vc = [[SignUpViewController alloc] initWithNibName:@"SignUpViewController" bundle:nil];
            [self.navigationController pushViewController:vc animated:true];
            break;
        }
        case 1: //log in
        {
            LogInViewController* vc = [[LogInViewController alloc] initWithNibName:@"LogInViewController" bundle:nil];
            [self.navigationController pushViewController:vc animated:true];
            break;
        }
    }
}

@end
