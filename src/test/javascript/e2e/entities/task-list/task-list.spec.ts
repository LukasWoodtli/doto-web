import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TaskListComponentsPage, TaskListDeleteDialog, TaskListUpdatePage } from './task-list.page-object';

const expect = chai.expect;

describe('TaskList e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taskListComponentsPage: TaskListComponentsPage;
  let taskListUpdatePage: TaskListUpdatePage;
  let taskListDeleteDialog: TaskListDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TaskLists', async () => {
    await navBarPage.goToEntity('task-list');
    taskListComponentsPage = new TaskListComponentsPage();
    await browser.wait(ec.visibilityOf(taskListComponentsPage.title), 5000);
    expect(await taskListComponentsPage.getTitle()).to.eq('dotoApp.taskList.home.title');
    await browser.wait(ec.or(ec.visibilityOf(taskListComponentsPage.entities), ec.visibilityOf(taskListComponentsPage.noResult)), 1000);
  });

  it('should load create TaskList page', async () => {
    await taskListComponentsPage.clickOnCreateButton();
    taskListUpdatePage = new TaskListUpdatePage();
    expect(await taskListUpdatePage.getPageTitle()).to.eq('dotoApp.taskList.home.createOrEditLabel');
    await taskListUpdatePage.cancel();
  });

  it('should create and save TaskLists', async () => {
    const nbButtonsBeforeCreate = await taskListComponentsPage.countDeleteButtons();

    await taskListComponentsPage.clickOnCreateButton();

    await promise.all([taskListUpdatePage.setNameInput('name')]);

    expect(await taskListUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await taskListUpdatePage.save();
    expect(await taskListUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taskListComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TaskList', async () => {
    const nbButtonsBeforeDelete = await taskListComponentsPage.countDeleteButtons();
    await taskListComponentsPage.clickOnLastDeleteButton();

    taskListDeleteDialog = new TaskListDeleteDialog();
    expect(await taskListDeleteDialog.getDialogTitle()).to.eq('dotoApp.taskList.delete.question');
    await taskListDeleteDialog.clickOnConfirmButton();

    expect(await taskListComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
