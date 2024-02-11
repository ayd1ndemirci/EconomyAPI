<?php

namespace ayd1ndemirci\EconomyAPI;

use ayd1ndemirci\EconomyAPI\command\AddMoneyCommand;
use ayd1ndemirci\EconomyAPI\command\MyMoneyCommand;
use ayd1ndemirci\EconomyAPI\command\SeeMoneyCommand;
use ayd1ndemirci\EconomyAPI\command\TakeMoneyCommand;
use ayd1ndemirci\EconomyAPI\database\SQLite;
use ayd1ndemirci\EconomyAPI\listener\PlayerListener;
use ayd1ndemirci\EconomyAPI\manager\Manager;
use pocketmine\plugin\PluginBase;

class Economy extends PluginBase
{
    public static Economy $economy;
    private SQLite $database;
    private Manager $manager;

    public function onLoad(): void
    {
        self::$economy = $this;
        $this->saveDefaultConfig();
        $this->database = new SQLite();
        $this->manager = new Manager();
    }

    protected function onEnable(): void
    {
        $this->getServer()->getCommandMap()->register("mymoney", new MyMoneyCommand($this));
        $this->getServer()->getCommandMap()->register("addmoney", new AddMoneyCommand($this));
        $this->getServer()->getCommandMap()->register("takemoney", new TakeMoneyCommand($this));
        $this->getServer()->getCommandMap()->register("seemoney", new SeeMoneyCommand($this));
        $this->getServer()->getPluginManager()->registerEvents(new PlayerListener(), $this);
    }

    protected function onDisable(): void
    {
        if ($dataConnector = $this->getDatabase()->getDataConnector()) $dataConnector->close();
    }

    /**
     * @return self
     */
    public static function getInstance(): self
    {
        return self::$economy;
    }

    /**
     * @return SQLite
     */
    public function getDatabase(): SQLite
    {
        return $this->database;
    }
    public function getManager(): Manager
    {
        return $this->manager;
    }
}